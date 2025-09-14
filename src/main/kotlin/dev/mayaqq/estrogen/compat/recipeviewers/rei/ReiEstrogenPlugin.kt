package dev.mayaqq.estrogen.compat.recipeviewers.rei

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.GenericRecipeViewerPlugin
import dev.mayaqq.estrogen.compat.recipeviewers.base.RVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.base.Role
import dev.mayaqq.estrogen.compat.recipeviewers.base.RvRecipeData
import dev.mayaqq.estrogen.compat.recipeviewers.base.ingredient.RvIngredient
import dev.mayaqq.estrogen.id
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.Display
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.entry.EntryIngredient
import me.shedaniel.rei.api.common.entry.EntryStack
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.minecraft.network.chat.Component
import java.util.*

object ReiEstrogenPlugin : REIClientPlugin {
    override fun getPluginProviderName(): String = id("rei_client").toString()

    override fun registerCategories(registry: CategoryRegistry) {
        GenericRecipeViewerPlugin.rvRecipes.forEach { data ->
            registry.add(ReiRecipe.Category(data))
        }
    }

    override fun registerDisplays(registry: DisplayRegistry) {
        GenericRecipeViewerPlugin.rvRecipes.forEach { data ->
            registry.registerRecipeFiller(data.actualRecipeClass.java, { type -> type == data.info.type }) { recipe ->
                ReiRecipe(data, data.recipeClass.constructors.first().call(recipe))
            }
        }
    }

    @Suppress("UnstableApiUsage")
    override fun registerBasicEntryFiltering(rule: BasicFilteringRule<*>) {
        GenericRecipeViewerPlugin.removedFromRecipeViewers.forEach {
            rule.hide(EntryStack.of(VanillaEntryTypes.ITEM, it))
        }
    }

    class ReiRecipe(val recipeData: RvRecipeData<*, *>, val rvRecipe: RVRecipe<*>) : BasicDisplay(
        rvRecipe.inputs().map { it.toRei() },
        rvRecipe.outputs().map { it.toRei() },
        Optional.of(rvRecipe.recipe.id)
    ) {
        override fun getCategoryIdentifier(): CategoryIdentifier<Display> = CategoryIdentifier.of(recipeData.info.id)

        class Category(val recipeData: RvRecipeData<*, *>) : DisplayCategory<Display> {
            override fun getCategoryIdentifier(): CategoryIdentifier<out Display> = CategoryIdentifier.of(recipeData.info.id)

            override fun getTitle(): Component = Text.translatable("${recipeData.info.id.namespace}.recipe.${recipeData.info.id.path}")

            override fun getIcon(): Renderer = Renderer { graphics, bounds, mouseX, mouseY, partialTick ->
                recipeData.info.render(graphics, bounds.centerX - 9, bounds.centerY - 9, partialTick)
            }

            override fun getDisplayHeight(): Int = recipeData.info.height
            override fun getDisplayWidth(display: Display): Int = recipeData.info.width

            override fun setupDisplay(display: Display, bounds: Rectangle): List<Widget> = buildList {
                val display = display as ReiRecipe
                add(Widgets.createRecipeBase(bounds))
                display.rvRecipe.textures.forEach { texture ->
                    add(Widgets.createDrawableWidget { graphics, mouseX, mouseY, delta ->
                        graphics.pushPop {
                            translate(bounds.getX().toDouble(), bounds.getY().toDouble() + 4, 0.0)
                            texture.coorded.render(graphics, texture.x, texture.y)
                        }
                    })
                }
                display.rvRecipe.drawables.forEach { drawable ->
                    add(Widgets.createDrawableWidget { graphics, mouseX, mouseY, delta ->
                        graphics.pushPop {
                            translate(bounds.getX().toDouble(), bounds.getY().toDouble() + 4, 0.0)
                            drawable.coorded.draw(graphics, drawable.x, drawable.y, mouseX, mouseY, delta)
                        }
                    })
                }
                display.rvRecipe.slots.forEach { slot ->
                    val reiSlot = Widgets.createSlot(Point(bounds.x + slot.x, bounds.y + slot.y)).disableBackground()
                    reiSlot.entries(slot.ingredient.toRei())
                    when (slot.role) {
                        Role.INPUT -> reiSlot.markInput()
                        Role.OUTPUT -> reiSlot.markOutput()
                        Role.CATALYST -> {}
                        Role.RENDER_ONLY -> {}
                    }
                    add(reiSlot)

                    add(Widgets.createDrawableWidget { graphics, mouseX, mouseY, delta ->
                        graphics.pushPop {
                            translate(bounds.getX().toDouble(), bounds.getY().toDouble() + 4, 0.0)
                            RecipeTextures.JEI_SLOT.render(graphics, slot.x - 1, slot.y - 1)
                        }
                    })
                }
            }
        }
    }
}

fun RvIngredient.toRei() : EntryIngredient = when {
    this.ingredient != null -> EntryIngredients.ofIngredient(this.ingredient)
    this.item != null -> EntryIngredients.of(this.item)
    this.fluid != null -> EntryIngredients.of(this.fluid)
    this.fluidTag != null -> EntryIngredients.ofFluidTag(this.fluidTag)
    else -> EntryIngredient.empty()
}