package dev.mayaqq.estrogen.compat.jei

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import mezz.jei.api.gui.drawable.IDrawable
import net.minecraft.client.gui.GuiGraphics

class JeiSlot(val texture: RecipeTextures) : IDrawable {
    override fun getWidth(): Int = texture.width
    override fun getHeight(): Int = texture.height
    override fun draw(graphics: GuiGraphics, xOffset: Int, yOffset: Int) = texture.render(graphics, xOffset, yOffset)
}