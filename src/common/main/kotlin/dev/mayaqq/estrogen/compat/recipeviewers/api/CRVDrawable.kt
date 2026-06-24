package dev.mayaqq.estrogen.compat.recipeviewers.api

import net.minecraft.client.gui.GuiGraphics

fun interface CRVDrawable {
    fun draw(graphics: GuiGraphics, offsetX: Int, offsetY: Int, mouseX: Int, mouseY: Int, delta: Float)
}