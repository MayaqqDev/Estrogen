package dev.mayaqq.estrogen.client.features.boobs

import dev.mayaqq.estrogen.content.EstrogenAttributes
import net.minecraft.util.Mth
import net.minecraft.world.entity.player.Player

object Boob {
    /**
     * Calculate boob size for rendering based on the time the effect was applied.
     *
     * @param startTime   The time when the effect was applied. World time in ticks.
     * @param currentTime Current world time in ticks.
     * @param initialSize Size between 0 and 1. Added to the calculated size.
     * @param tickDelta   Time elapsed since the last tick.
     * @return Boob size. Floating point value between 0 and 1.
     */
    fun boobSize(startTime: Double, currentTime: Double, initialSize: Float, tickDelta: Float): Float {
        return Mth.clamp(((currentTime - startTime + tickDelta).toFloat() * 50 / 20000) + initialSize, 0.0f, 1.0f)
    }

    fun shouldShow(player: Player): Boolean {
        return player.getAttributeValue(EstrogenAttributes.SHOW_BOOBS) > 0.0
    }

    @JvmStatic
    fun boobFunc(level: Float): Float {
        return 1.48f - Mth.invSqrt((level + 0.6f) * 0.95f)
    }
}