package dev.mayaqq.estrogen.features.dash

import java.util.*
import java.util.concurrent.ConcurrentHashMap

object CommonDash {
    private val DASH_DURATION = 300

    private val players = ConcurrentHashMap<UUID, Long>()

    fun setDashing(player: UUID) {
        players[player] = System.currentTimeMillis()
    }

    fun removeDashing(player: UUID) {
        players.remove(player)
    }

    fun isDashing(player: UUID): Boolean {
        return players.getOrDefault(player, 0L) + DASH_DURATION > System.currentTimeMillis()
    }
}