package dev.mayaqq.estrogen.client.features.boobs

import net.minecraft.client.Minecraft
import net.minecraft.world.entity.player.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object BoobPhysicsManager {
    private val players = ConcurrentHashMap<UUID, Physics>()

    fun isEnabled(): Boolean {
        return TODO("EstrogenConfig.client().chestPhysicsRendering.get()")
    }

    fun tick() {
        val level = Minecraft.getInstance().level
        if (level == null) return
        if (!isEnabled()) return

        for (physics in players.entries) {
            val player = level.getPlayerByUUID(physics.key)
            if (player != null && Boob.shouldShow(player)) {
                physics.value.update(player)
                if (physics.value.expired) {
                    players.remove(physics.key)
                }
            } else {
                players.remove(physics.key)
            }
        }
    }

    fun getPhysicsForPlayer(player: Player): Physics {
        return players.computeIfAbsent(player.getUUID()) { uuid: UUID -> Physics() }
    }
}