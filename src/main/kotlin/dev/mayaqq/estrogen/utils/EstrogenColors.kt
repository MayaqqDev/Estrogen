package dev.mayaqq.estrogen.utils

import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.darker
import invoke.kitty.kritter.utils.color.lighter
import invoke.kitty.kritter.utils.color.rgb
import invoke.kitty.kritter.utils.color.rgba

object EstrogenColors {
    val MOLTEN_SLIME = rgba(144, 238, 144, 255)
    val TESTOSTERONE_MIXTURE = rgba(232, 212, 170, 255)
    val FILTRATED_HORSE_URINE = rgba(225, 225, 20, 255)
    val HORSE_URINE = rgba(140, 139, 5, 255)
    val MOLTEN_AMETHYST = rgba(174, 122, 253, 255)
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