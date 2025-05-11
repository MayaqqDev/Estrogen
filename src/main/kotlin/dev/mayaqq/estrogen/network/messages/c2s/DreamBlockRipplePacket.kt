package dev.mayaqq.estrogen.network.messages.c2s

import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.ServerNetworkContext
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.utils.toBlockPos
import dev.mayaqq.estrogen.content.EstrogenSounds
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundSource
import net.minecraft.world.phys.Vec3

@Packet("dream_ripple")
data class DreamBlockRipplePacket(val position: Vec3, val face: Direction) : Packet.Serverbound {

    companion object {
        val CODEC: ByteCodec<DreamBlockRipplePacket> = ObjectByteCodec.create(
            ByteCodecs.VEC3 fieldOf DreamBlockRipplePacket::position,
            ByteCodec.ofEnum(Direction::class.java) fieldOf DreamBlockRipplePacket::face,
            ::DreamBlockRipplePacket
        )
    }

    override fun ServerNetworkContext.handle() = execute {
        // TODO: Play dream block hit sound and spawn ripple particle
    }
}
