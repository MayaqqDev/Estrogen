package dev.mayaqq.estrogen.compat.jei

import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import mezz.jei.api.gui.drawable.IDrawable
import net.minecraft.client.gui.GuiGraphics

class StackWIthCatalystJeiRenderable(val viewerInfo: RecipeViewerInfo) : IDrawable {
    override fun getWidth(): Int = 16
    override fun getHeight(): Int = 16

    override fun draw(graphics: GuiGraphics, xOffset: Int, yOffset: Int) {
        viewerInfo.render(graphics, xOffset, yOffset, 0F)
    }
}