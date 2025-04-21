package dev.mayaqq.estrogen.network.messages.s2c

import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.network.ClientNetworkContext
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.serialization.KByteCodec
import dev.mayaqq.cynosure.utils.codecs.fieldOf
import dev.mayaqq.estrogen.config.ChestConfig
import kotlinx.serialization.Serializable
import java.util.*

@Packet("player_chest_config")
data class ChestConfigPacket(val uuid: UUID, val config: ChestConfig) : Packet.Clientbound {

    companion object {
        val CODEC: ByteCodec<ChestConfigPacket> = ObjectByteCodec.create(
            ByteCodec.STRING.map(UUID::fromString, UUID::toString) fieldOf ChestConfigPacket::uuid,
            KByteCodec(ChestConfig.serializer()) fieldOf ChestConfigPacket::config,
            ::ChestConfigPacket
        )
    }

    override fun ClientNetworkContext.handle() {
        TODO("Not yet implemented")
    }
}
