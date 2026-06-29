package dev.mayaqq.estrogen.network.messages.c2s

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.minecraft.core.Direction
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3

@Serializable
data class DreamBlockRipplePacket(val position: @Contextual Vec3, val face: Direction) {

    fun handle(server: MinecraftServer, sender: ServerPlayer) {
        // TODO: Play dream block hit sound and spawn ripple particle
    }
}
