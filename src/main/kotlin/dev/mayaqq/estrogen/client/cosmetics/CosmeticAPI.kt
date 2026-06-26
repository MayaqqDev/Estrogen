package dev.mayaqq.estrogen.client.cosmetics

import com.mojang.serialization.JsonOps
import com.teamresourceful.resourcefulcosmetics.ResourcefulCosmetics
import com.teamresourceful.resourcefulcosmetics.SignedData
import com.teamresourceful.resourcefulcosmetics.errors.*
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.utils.serialization.buildClassSerializer
import dev.mayaqq.cynosure.utils.serialization.fieldOf
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.c2s.UpdatedCosmeticPacket
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Player
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.CompletableFuture


object CosmeticAPI : Logger by LoggerFactory.getLogger("Estrogen Cosmetics") {

    private val DISABLED: MutableSet<UUID> = mutableSetOf()

    fun id(path: String) = identifier("estrogencosmetics", path)

    internal val API: ResourcefulCosmetics<Cosmetic> = ResourcefulCosmetics.create(
        "https://estrogen-cosmetics.teamresourceful.com/",
        true,
        { id, json ->
            Cosmetic.codec(id).parse(JsonOps.INSTANCE, json).orThrow
        }
    ) { error -> Estrogen.error("Failed to load cosmetics", error) }

    fun getCosmetic(id: String): Cosmetic? = API.getCosmetic(id)

    fun hasSessionToken(): Boolean = API.hasSession(McClient.user.profileId)

    fun getAvailableCosmetics(): List<String> {
        return try {
            API.getCosmetics(McClient.user.profileId, true).available()
        } catch (_: Exception) {
            listOf()
        }
    }

    private fun call(call: () -> Unit): CompletableFuture<StatusCode> = CompletableFuture.supplyAsync {
        try {
            call.invoke()
            StatusCode.OK
        } catch (e: Exception) {
            when(e) {
                is BadRequestException -> StatusCode.BAD_REQUEST
                is ForbiddenException -> StatusCode.FORBIDDEN
                is InternalServerException -> StatusCode.INTERNAL_SERVER_ERROR
                is NotFoundException -> StatusCode.NOT_FOUND
                is UnauthorizedException -> StatusCode.UNAUTHORIZED
                else -> {
                    Estrogen.error("Failed to call API", e)
                    StatusCode.UNKNOWN_ERROR
                }
            }
        }
    }

    fun login(): CompletableFuture<StatusCode> {
        return call {
            val user = McClient.user
            val serverId = UUID.randomUUID().toString()
            McClient.minecraftSessionService.joinServer(user.profileId, user.accessToken, serverId)
            API.login(user.profileId, user.name, serverId)
        }
    }

    fun getCosmetics(): CompletableFuture<StatusCode> = call { API.getCosmetics(profileId, false) }

    fun setCosmetic(cosmetic: Cosmetic?): CompletableFuture<StatusCode> = call {
        val data = API.setAndGetCosmetic(profileId, cosmetic?.id)
        McClient.connection?.let {
            data?.let {
                EstrogenNetwork.sendToServer(UpdatedCosmeticPacket(it))
            }
        }
    }

    fun updateCosmetic(signedData: SignedData) = try {
        API.updateCosmetics(signedData)
    } catch (e: Exception) {
        Estrogen.error("Error updating cosmetic with error: ", e)
    }

    fun claimReward(code: String): CompletableFuture<StatusCode> {
        return call { API.claimCosmetic(profileId, code) }
    }

    fun getCosmetic(uuid: UUID): Cosmetic? = CosmeticAPI.API.getSelectedCosmetic(uuid)
    fun getCosmetic(player: Player): Cosmetic? = this.getCosmetic(player.uuid)
    fun setCosmeticShown(player: UUID, shown: Boolean) {
        if (shown) DISABLED.remove(player)
        else DISABLED.add(player)
    }
    fun setCosmeticShown(player: Player, shown: Boolean) = setCosmeticShown(player.uuid, shown)
}

fun Player.getCosmetic(): Cosmetic? = CosmeticAPI.getCosmetic(this)
fun UUID.getCosmetic(): Cosmetic? = CosmeticAPI.getCosmetic(this)
fun Player.setCosmeticShown(shown: Boolean) = CosmeticAPI.setCosmeticShown(this, shown)
fun UUID.setCosmeticShown(shown: Boolean) = CosmeticAPI.setCosmeticShown(this, shown)

private val profileId get() = McClient.user.profileId

enum class StatusCode {
    OK,
    BAD_REQUEST,
    FORBIDDEN,
    UNAUTHORIZED,
    NOT_FOUND,
    INTERNAL_SERVER_ERROR,

    UNKNOWN_ERROR,
}

object SignedDataSerializer : KSerializer<SignedData> by buildClassSerializer("",
    String.serializer().fieldOf("data", SignedData::data),
    String.serializer().fieldOf("signature", SignedData::signature),
    ::SignedData
)