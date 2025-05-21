package dev.mayaqq.estrogen.compat.emi

import dev.emi.emi.api.render.EmiRenderable
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.item.ItemStack

class StackWithCatalystEmiRenderable(val viewerInfo: RecipeViewerInfo) : EmiRenderable {
    override fun render(graphics: GuiGraphics, x: Int, y: Int, delta: Float) {
        viewerInfo.render(graphics, x, y, delta)
    }
}