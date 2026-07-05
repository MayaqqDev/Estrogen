@file:UseSerializers(Vec3Serializer::class, ResourceLocationSerializer::class)
package dev.mayaqq.estrogen.network.messages.c2s

import dev.mayaqq.cynosure.utils.toBlockPos
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.serialization.builtins.ResourceLocationSerializer
import invoke.kitty.kritter.serialization.builtins.Vec3Serializer
import invoke.kitty.kritter.serialization.builtins.vectors.Vector3fSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.phys.Vec3

@Serializable @JvmRecord
data class SpawnHeartsPacket(val pos: Vec3, val ambientSound: ResourceLocation) {

    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        val level = sender.serverLevel()
        level.sendParticles(ParticleTypes.HEART, pos.x(), pos.y(), pos.z(), 1, 0.5, 0.5, 0.5, 0.5)
        if (ambientSound != id("empty")) {
            val blockPos = pos.toBlockPos()
            val event = SoundEvent.createVariableRangeEvent(ambientSound)
            level.playSound(null, blockPos, event, SoundSource.PLAYERS, 1.0f, 10.0f)
            sender.swing(InteractionHand.MAIN_HAND, true)
        }
    }
}
