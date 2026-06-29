package dev.mayaqq.estrogen.network.messages.s2c

import dev.mayaqq.estrogen.config.types.ChestConfig
import dev.mayaqq.estrogen.injection.chestConfig
import kotlinx.serialization.Serializable
import net.minecraft.client.Minecraft
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class ChestConfigPacket(val uuid: Uuid, val config: ChestConfig) {

    fun handle() {
        Minecraft.getInstance().level?.getPlayerByUUID(uuid.toJavaUuid())?.chestConfig = config
    }
}
