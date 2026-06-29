package dev.mayaqq.estrogen.compat.recipeviewers.api

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import net.minecraft.core.Holder
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeHolder
import kotlin.reflect.KClass

abstract class CRVRecipe<T : Recipe<*>>(val recipe: RecipeHolder<T>) {
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

    data class ObjectWithCoords<T>(val coorded: T, val x: Int, val y: Int)
}

open class ViewerInfo<T : Recipe<*>, C : CRVRecipe<T>>(
    val info: RecipeViewerInfo,
    val crvrecipe: (RecipeHolder<Recipe<*>>) -> C,
    val recipeClass: KClass<T>
)

data class Slot(
    var ingredient: CRVIngredient,
    var x: Int,
    var y: Int,
    var role: Role = Role.RENDER_ONLY,
    var background: RecipeTextures = RecipeTextures.JEI_SLOT
)

enum class Role { INPUT, OUTPUT, CATALYST, RENDER_ONLY }