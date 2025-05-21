package dev.mayaqq.estrogen.compat.rei

import dev.mayaqq.cynosure.client.utils.pushPop
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.item.ItemStack

class StackWithCatalystRmiRenderable(val stack: ItemStack, val catalyst: ItemStack) : Renderer {
    override fun render(graphics: GuiGraphics, bounds: Rectangle, mouseX: Int, mouseY: Int, delta: Float) {
        graphics.renderFakeItem(stack, 0, 0)
        graphics.pushPop {
            scale(0.5f, 0.5f, 0.5f)
            graphics.renderFakeItem(catalyst, 8, 8)
        }
    }
}