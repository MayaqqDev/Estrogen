package dev.mayaqq.estrogen.network.messages.c2s

import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.ServerNetworkContext
import dev.mayaqq.cynosure.utils.bytecodecs.ExtraByteCodecs
import dev.mayaqq.cynosure.utils.codecs.fieldOf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f

@Packet("spawn_hearts")
data class SpawnHeartsPacket(val pos: Vector3f, val ambientSound: ResourceLocation) : Packet.Serverbound {

    companion object {
        val CODEC: ByteCodec<SpawnHeartsPacket> = ObjectByteCodec.create(
            ExtraByteCodecs.VECTOR_3F fieldOf SpawnHeartsPacket::pos,
            ExtraByteCodecs.RESOURCE_LOCATION fieldOf SpawnHeartsPacket::ambientSound,
            ::SpawnHeartsPacket
        )
    }

    constructor(pos: Vec3, ambientSound: ResourceLocation) : this(pos.toVector3f(), ambientSound)

    override fun ServerNetworkContext.handle() {
        TODO("Not yet implemented")
    }
}
