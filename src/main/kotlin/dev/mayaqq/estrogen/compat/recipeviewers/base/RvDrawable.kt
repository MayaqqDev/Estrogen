package dev.mayaqq.estrogen.compat.recipeviewers.base

import net.minecraft.client.gui.GuiGraphics

fun interface RvDrawable {
    fun draw(graphics: GuiGraphics, offsetX: Int, offsetY: Int, delta: Float)
}