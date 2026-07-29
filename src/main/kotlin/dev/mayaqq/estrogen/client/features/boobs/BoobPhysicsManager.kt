package dev.mayaqq.estrogen.client.features.boobs

import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.helpers.McLevel
import dev.mayaqq.estrogen.config.EstrogenClientConfig
import net.minecraft.world.entity.player.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap


object BoobPhysicsManager {
    private val players = ConcurrentHashMap<UUID, Physics>()

    fun isEnabled(): Boolean = EstrogenClientConfig.ChestRenderingGlobal.physicsRendering

    @Subscription
    fun tick(event: ClientTickEvent.Begin) {
        val level = McLevel?: return
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
        return players.computeIfAbsent(player.uuid) { _ -> Physics() }
    }
}