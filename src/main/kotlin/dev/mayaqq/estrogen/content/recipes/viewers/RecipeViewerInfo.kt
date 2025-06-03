package dev.mayaqq.estrogen.content.recipes.viewers

import com.mojang.blaze3d.systems.RenderSystem
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

    fun render(graphics: GuiGraphics, offsetX: Int, offsetY: Int, delta: Float) {

        RenderSystem.enableDepthTest()
        graphics.pushPop {
            translate(offsetX.toDouble(), offsetY.toDouble(), 0.0)

            pushPop {
                translate(1f, 1f, 0f)
                graphics.renderFakeItem(display, 0, 0)
            }

            pushPop {
                translate(10f, 10f, 100f)
                scale(.5f, .5f, .5f)
                graphics.renderFakeItem(catalyst, 0, 0)
            }
        }

    }
}