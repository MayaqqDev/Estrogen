package dev.mayaqq.estrogen.utils

import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.darker
import dev.mayaqq.cynosure.utils.colors.lighter

object EstrogenColors {
    val MOLTEN_SLIME = Color(144, 238, 144)
    val TESTOSTERONE_MIXTURE = Color(232, 212, 170)
    val FILTRATED_HORSE_URINE = Color(225, 225, 20)
    val HORSE_URINE = Color(140, 139, 5)
    val MOLTEN_AMETHYST = Color(174, 122, 253)
    val DREAM_BLOCK = Color(0.2f, 0f, 0.2f)

    val ESTROGEN_PATCHES_BAR = Color(0, 179, 255)

    val MOTH_YELLOW = Color(255, 197, 20)
    val MOTH_PINK = Color(255, 131, 192)

    private val DASH_OVERLAY = arrayOf(
        Color(77, 128, 204),
        Color(253, 126, 247).darker()
    )

    fun getDashColor(level: Int, particle: Boolean): Color {
        val level = when {
            level < 1 -> 1
            level > DASH_OVERLAY.size -> DASH_OVERLAY.size
            else -> level
        }
        return if (particle) DASH_OVERLAY[level - 1].lighter() else DASH_OVERLAY[level - 1]
    }
}