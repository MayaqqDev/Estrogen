package dev.mayaqq.estrogen.content.recipes.viewers

import dev.mayaqq.cynosure.client.utils.pushPop
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

interface RecipeViewerInfo {
    val display: ItemStack
    val catalyst: ItemStack
    val id: ResourceLocation
    val width: Int
    val height: Int

    fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        graphics.renderFakeItem(display, 0, 0)
        graphics.pushPop {
            scale(0.5f, 0.5f, 0.5f)
            graphics.renderFakeItem(catalyst, 8, 8)
        }
    }
}