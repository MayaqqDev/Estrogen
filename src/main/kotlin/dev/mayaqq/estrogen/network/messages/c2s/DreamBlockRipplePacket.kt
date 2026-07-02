@file:UseSerializers(Vec3Serializer::class)
package dev.mayaqq.estrogen.network.messages.c2s

import invoke.kitty.kritter.serialization.builtins.Vec3Serializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.minecraft.core.Direction
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3

@Serializable @JvmRecord
data class DreamBlockRipplePacket(val position: Vec3, val face: Direction) {

    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        // TODO: Play dream block hit sound and spawn ripple particle
    }
}
