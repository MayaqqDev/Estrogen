package dev.mayaqq.estrogen.compat.rei

import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import net.minecraft.client.gui.GuiGraphics

class StackWithCatalystReiRenderable(val viewerInfo: RecipeViewerInfo) : Renderer {
    override fun render(graphics: GuiGraphics, bounds: Rectangle, mouseX: Int, mouseY: Int, delta: Float) {
        viewerInfo.render(graphics, bounds.centerX - 9, bounds.centerY - 9, delta)
    }
}