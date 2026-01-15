package dev.mayaqq.estrogen.compat.recipeviewers.api

import com.mojang.blaze3d.systems.RenderSystem
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe.ObjectWithCoords
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import kotlin.reflect.KClass

abstract class CRVPseudoRecipe<T : RecipeData>(val data: T) {
    val textures = mutableListOf<ObjectWithCoords<RecipeTextures>>()
    val slots = mutableListOf<Slot>()
    val drawables = mutableListOf<ObjectWithCoords<CRVDrawable>>()

    abstract fun init()

    abstract val inputs: List<CRVIngredient>
    abstract val outputs: List<CRVIngredient>
    abstract val catalysts: List<CRVIngredient>

    fun addTexture(texture: RecipeTextures, x: Int, y: Int) = textures.add(ObjectWithCoords(texture, x, y))
    fun addSlot(rvIngredient: CRVIngredient, x: Int, y: Int, role: Role): Slot = Slot(rvIngredient, x, y, role).apply { slots.add(this) }
    fun addDrawable(x: Int, y: Int, drawable: CRVDrawable) = drawables.add(ObjectWithCoords(drawable, x, y))

    abstract fun getId(): ResourceLocation
}

class PseudoRecipeHolder<T : RecipeData>(
    val dataSupplier: () -> List<T>,
    val builder: (T) -> CRVPseudoRecipe<T>,
    val display: ItemStack,
    val catalyst: ItemStack,
    val id: ResourceLocation,
    val width: Int,
    val height: Int,
) {
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

interface RecipeData