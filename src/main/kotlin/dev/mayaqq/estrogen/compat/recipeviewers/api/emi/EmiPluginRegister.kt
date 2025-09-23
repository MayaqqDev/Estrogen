package dev.mayaqq.estrogen.compat.recipeviewers.api.emi

import dev.emi.emi.api.recipe.BasicEmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.SlotWidget
import dev.emi.emi.api.widget.WidgetHolder
import dev.emi.emi.registry.EmiPluginContainer
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CommonRecipeViewer

object EmiPluginRegister {
    fun getPlugins(): List<EmiPluginContainer> {
        return CommonRecipeViewer.getPlugins().map { commonPlugin ->
            EmiPluginContainer({ registry ->
                commonPlugin.plugin.recipes.forEach { viewerInfo ->
                    val category = EmiRecipeCategory(viewerInfo.info.id) {graphics, offsetX, offsetY, partialTick ->
                        viewerInfo.info.render(graphics, offsetX, offsetY, partialTick)
                    }

                    registry.addCategory(category)

                    registry.recipeManager.recipes.filter { it.type == viewerInfo.info.type }.forEach { recipe ->
                        val recipe = viewerInfo.crvrecipe.invoke(recipe)
                        registry.addRecipe(object : BasicEmiRecipe(category, viewerInfo.info.id, viewerInfo.info.width, viewerInfo.info.height) {
                            init {
                                recipe.init()

                                this.inputs.addAll(recipe.inputs.map { it.toEmi() })
                                this.outputs.addAll(recipe.outputs.map { it.toEmiStack() })
                                this.catalysts.addAll(recipe.catalysts.map { it.toEmi() })
                            }

                            override fun addWidgets(widgets: WidgetHolder) {
                                recipe.textures.forEach { texture ->
                                    widgets.addTexture(texture.coorded, texture.x, texture.y)
                                }
                                recipe.slots.forEach { slot ->
                                    widgets.addSlot(slot.ingredient.toEmi(), slot.x, slot.y).withBackground(slot.background)
                                }
                                recipe.drawables.forEach { drawable ->
                                    widgets.addDrawable(drawable.x, drawable.y, 0, 0) {graphics, mouseX, mouseY, partialTick  ->
                                        drawable.coorded.draw(graphics, 0, 0, mouseX, mouseY, partialTick)
                                    }
                                }
                            }

                        })
                    }

                }

                //Hiding
                commonPlugin.plugin.removedItems.forEach { registry.removeEmiStacks(EmiStack.of(it)) }
                commonPlugin.plugin.removedRecipes.forEach { registry.removeRecipes(it) }
            }, commonPlugin.modid)
        }
    }
}

fun CRVIngredient.toEmi(): EmiIngredient = when {
    this.ingredient != null -> EmiIngredient.of(this.ingredient)
    this.item != null -> EmiStack.of(item)
    this.fluid != null -> EmiStack.of(fluid)
    this.fluidTag != null -> EmiIngredient.of(fluidTag)
    else -> EmiStack.EMPTY
}

fun CRVIngredient.toEmiStack(): EmiStack = when {
    this.item != null -> EmiStack.of(item)
    this.fluid != null -> EmiStack.of(fluid)
    else -> EmiStack.EMPTY
}

fun WidgetHolder.addTexture(texture: RecipeTextures, x: Int, y: Int) {
    this.addTexture(EmiTexture(texture.textureLocation, texture.startX, texture.startY, texture.width, texture.height), x, y)
}

fun SlotWidget.withBackground(texture: RecipeTextures): SlotWidget = this.customBackground(texture.textureLocation, texture.startX, texture.startY, texture.width, texture.height)