package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.estrogen.injection.flap
import kotlinx.serialization.Serializable
import net.minecraft.client.Minecraft
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@OptIn(ExperimentalUuidApi::class)
@Serializable @JvmRecord
data class FlapSyncPacket(val flaps: Int, val player: Uuid) {
    fun handle() {
        val level = Minecraft.getInstance().level
        val player = level?.getPlayerByUUID(player.toJavaUuid()) ?: return

        player.flap()
    }
}