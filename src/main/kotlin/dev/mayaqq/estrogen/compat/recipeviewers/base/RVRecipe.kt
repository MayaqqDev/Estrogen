package dev.mayaqq.estrogen.compat.recipeviewers.base

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.base.ingredient.RvIngredient
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.Container
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeType
import kotlin.reflect.KClass

abstract class RVRecipe<T : Recipe<*>>(
    val recipe: T,
) {
    val textures = mutableListOf<ObjectWithCoords<RecipeTextures>>()
    val slots = mutableListOf<Slot>()
    val drawables = mutableListOf<ObjectWithCoords<RvDrawable>>()

    abstract fun init()

    abstract fun inputs(): List<RvIngredient>
    abstract fun outputs(): List<RvIngredient>
    abstract fun catalysts(): List<RvIngredient>

    fun addTexture(texture: RecipeTextures, x: Int, y: Int) = textures.add(ObjectWithCoords(texture, x, y))
    fun addSlot(rvIngredient: RvIngredient, x: Int, y: Int): Slot = Slot(rvIngredient, x, y).apply { slots.add(this) }
    fun addDrawable(x: Int, y: Int, drawable: RvDrawable) = drawables.add(ObjectWithCoords(drawable, x, y))

    data class ObjectWithCoords<T>(val coorded: T, val x: Int, val y: Int)


}

open class RvRecipeData<T : Recipe<*>, C : RVRecipe<T>>(val info: RecipeViewerInfo, val recipeClass: KClass<C>)

class Slot(var ingredient: RvIngredient, var x: Int, var y: Int, var background: RecipeTextures = RecipeTextures.JEI_SLOT)