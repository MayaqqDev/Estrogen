package dev.mayaqq.estrogen.compat.rei

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.item.ItemStack

class StackWithCatalystRmiRenderable(val viewerInfo: RecipeViewerInfo) : Renderer {
    override fun render(graphics: GuiGraphics, bounds: Rectangle, mouseX: Int, mouseY: Int, delta: Float) {
        viewerInfo.render(graphics, mouseX, mouseY, delta)
    }
}