package dev.mayaqq.estrogen.network.messages.c2s

import com.teamresourceful.bytecodecs.base.ByteCodec
import com.teamresourceful.bytecodecs.base.`object`.ObjectByteCodec
import dev.mayaqq.cynosure.network.Packet
import dev.mayaqq.cynosure.network.ServerNetworkContext
import dev.mayaqq.cynosure.core.bytecodecs.ByteCodecs
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.utils.toBlockPos
import dev.mayaqq.estrogen.id
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f

@Packet("spawn_hearts")
data class SpawnHeartsPacket(val pos: Vector3f, val ambientSound: ResourceLocation) : Packet.Serverbound {

    companion object {
        val CODEC: ByteCodec<SpawnHeartsPacket> = ObjectByteCodec.create(
            ByteCodecs.VECTOR_3F fieldOf SpawnHeartsPacket::pos,
            ByteCodecs.RESOURCE_LOCATION fieldOf SpawnHeartsPacket::ambientSound,
            ::SpawnHeartsPacket
        )
    }

    constructor(pos: Vec3, ambientSound: ResourceLocation) : this(pos.toVector3f(), ambientSound)

    override fun ServerNetworkContext.handle() = execute {
        val level = sender.serverLevel()
        level.sendParticles(ParticleTypes.HEART, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 1, 0.5, 0.5, 0.5, 0.5)
        if (ambientSound != id("empty")) {
            val blockPos = pos.toBlockPos()
            val event = SoundEvent.createVariableRangeEvent(ambientSound)
            level.playSound(null as? Entity, blockPos, event, SoundSource.PLAYERS, 1.0f, 10.0f)
            sender.swing(InteractionHand.MAIN_HAND, true)
        }
    }
}
