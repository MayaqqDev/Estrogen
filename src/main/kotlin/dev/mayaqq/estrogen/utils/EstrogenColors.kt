package dev.mayaqq.estrogen.utils

import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.darker
import invoke.kitty.kritter.utils.color.lighter
import invoke.kitty.kritter.utils.color.rgb

object EstrogenColors {
    val MOLTEN_SLIME = rgb(144, 238, 144)
    val TESTOSTERONE_MIXTURE = rgb(232, 212, 170)
    val FILTRATED_HORSE_URINE = rgb(225, 225, 20)
    val HORSE_URINE = rgb(140, 139, 5)
    val MOLTEN_AMETHYST = rgb(174, 122, 253)
    val DREAM_BLOCK = rgb(0.2f, 0f, 0.2f)

    val ESTROGEN_PATCHES_BAR = rgb(0, 179, 255)

    val MOTH_YELLOW = rgb(255, 197, 20)
    val MOTH_PINK = rgb(255, 131, 192)

    private val DASH_OVERLAY = arrayOf(
        rgb(77, 128, 204),
        rgb(253, 126, 247).darker()
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