package dev.mayaqq.estrogen.compat.emi

import dev.emi.emi.api.render.EmiRenderable
import dev.mayaqq.cynosure.client.utils.pushPop
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.item.ItemStack

class StackWithCatalystEmiRenderable(val stack: ItemStack, val catalyst: ItemStack) : EmiRenderable {
    override fun render(graphics: GuiGraphics, x: Int, y: Int, delta: Float) {
        graphics.renderFakeItem(stack, x, y)
        graphics.pushPop {
            scale(0.5f, 0.5f, 0.5f)
            graphics.renderFakeItem(catalyst, x + 8, y + 8)
        }
    }
}