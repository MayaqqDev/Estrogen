package dev.mayaqq.estrogen.compat.emi

import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.BasicEmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.SlotWidget
import dev.emi.emi.api.widget.WidgetHolder
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.GenericRecipeViewerPlugin
import dev.mayaqq.estrogen.compat.recipeviewers.base.ingredient.RvIngredient
@EmiEntrypoint
object EmiEstrogenPlugin : EmiPlugin {


    override fun register(registry: EmiRegistry) {

        GenericRecipeViewerPlugin.rvRecipes.forEach { rvrecipeinfo ->
            val category = EmiRecipeCategory(rvrecipeinfo.info.id) {graphics, offsetX, offsetY, partialTick ->
                rvrecipeinfo.info.render(graphics, offsetX, offsetY, partialTick)
            }

            registry.addCategory(category)

            registry.recipeManager.recipes.filter { it.type == rvrecipeinfo.info.type }.forEach { recipe ->
                val rvrecipe = rvrecipeinfo.recipeClass.constructors.first().call(recipe)
                registry.addRecipe(object : BasicEmiRecipe(category, rvrecipeinfo.info.id, rvrecipeinfo.info.width, rvrecipeinfo.info.height) {
                    init {
                        rvrecipe.init()

                        this.inputs.addAll(rvrecipe.inputs().map { it.toEmi() })
                        this.outputs.addAll(rvrecipe.outputs().map { it.toEmiStack() })
                        this.catalysts.addAll(rvrecipe.catalysts().map { it.toEmi() })
                    }

                    override fun addWidgets(widgets: WidgetHolder) {
                        rvrecipe.textures.forEach { texture ->
                            widgets.addTexture(texture.coorded, texture.x, texture.y)
                        }
                        rvrecipe.slots.forEach { slot ->
                            widgets.addSlot(slot.ingredient.toEmi(), slot.x, slot.y).withBackground(slot.background)
                        }
                        rvrecipe.drawables.forEach { drawable ->
                            widgets.addDrawable(drawable.x, drawable.y, 0, 0) {graphics, offsetX, offsetY, partialTick  ->
                                drawable.coorded.draw(graphics, offsetX, offsetY, partialTick)
                            }
                        }
                    }

                })
            }

        }

        //Hiding
        GenericRecipeViewerPlugin.removedFromRecipeViewers.forEach { registry.removeEmiStacks(EmiStack.of(it)) }
        GenericRecipeViewerPlugin.removedRecipesFromRecipeViewers.forEach { registry.removeRecipes(it) }
    }
}

fun RvIngredient.toEmi(): EmiIngredient = when {
    this.ingredient != null -> EmiIngredient.of(this.ingredient)
    this.item != null -> EmiStack.of(item)
    this.fluid != null -> EmiStack.of(fluid)
    this.fluidTag != null -> EmiIngredient.of(fluidTag)
    else -> EmiStack.EMPTY
}

fun RvIngredient.toEmiStack(): EmiStack = when {
    this.item != null -> EmiStack.of(item)
    this.fluid != null -> EmiStack.of(fluid)
    else -> EmiStack.EMPTY
}

fun WidgetHolder.addTexture(texture: RecipeTextures, x: Int, y: Int) {
    this.addTexture(EmiTexture(texture.textureLocation, texture.startX, texture.startY, texture.width, texture.height), x, y)
}

fun SlotWidget.withBackground(texture: RecipeTextures): SlotWidget = this.customBackground(texture.textureLocation, texture.startX, texture.startY, texture.width, texture.height)