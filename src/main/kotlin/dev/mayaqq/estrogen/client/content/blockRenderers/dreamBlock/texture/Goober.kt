package dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture

import dev.mayaqq.cynosure.utils.colors.Color
import it.unimi.dsi.fastutil.ints.IntList
import net.minecraft.resources.ResourceLocation
import dev.mayaqq.cynosure.utils.colors.Colors as DefaultColors

data class Goober(val texture: Texture, val x: Int, val y: Int, val color: Color) {
    data class Texture(val location: ResourceLocation, val width: Int, val height: Int, val frameHeight: Int, val frametime: Int, val frames: IntList) {
        fun getFrameOffset(ticks: Int): Int = frames.getInt((ticks / frametime) % frames.size)
    }

    enum class Colors(val color: Color) {
        YELLOW(DefaultColors.YELLOW),
        CYAN(Color(0, 241, 254)),
        PURPLE(Color(126, 126, 218)),
        MAGENTA(Color(255, 71, 231)),
        GREEN1(Color(60, 145, 97)),
        GREEN2(Color(40, 198, 53));

        val value: Int = color.argb.toInt()
    }
}
