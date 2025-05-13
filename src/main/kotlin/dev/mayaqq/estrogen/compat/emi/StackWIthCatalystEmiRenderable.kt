package dev.mayaqq.estrogen.compat.emi

import dev.emi.emi.api.render.EmiRenderable
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class StackWithCatalystEmiRenderable(val stack: ItemStack, val catalyst: ResourceLocation) : EmiRenderable {
    override fun render(graphics: GuiGraphics, x: Int, y: Int, delta: Float) {
        graphics.renderFakeItem(stack, x, y)
        graphics.blit(catalyst, x + 8, x + y, 0, 0, 8, 8)
    }
}