package dev.mayaqq.estrogen.compat.recipeviewers.api.rei

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.CommonRecipeViewer
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
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
import me.shedaniel.rei.api.common.plugins.PluginView
import me.shedaniel.rei.api.common.plugins.REIPluginProvider
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.util.Optional

object ReiPluginRegister {
    fun register() {
        getPlugins().forEach { pluginProvider ->
            PluginView.getClientInstance().registerPlugin(pluginProvider)
        }
    }

    fun getPlugins(): List<REIPluginProvider<REIClientPlugin>> {
        return CommonRecipeViewer.getPlugins().map { commonPlugin ->
            object : REIPluginProvider<REIClientPlugin> {

                val plugin = object : REIClientPlugin {
                    override fun getPluginProviderName(): String = ResourceLocation(commonPlugin.modid, "rei_client").toString()

                    override fun registerCategories(registry: CategoryRegistry) {
                        commonPlugin.plugin.recipes.forEach { data ->
                            registry.add(ReiCategory(data))
                        }
                    }

                    override fun registerDisplays(registry: DisplayRegistry) {
                        commonPlugin.plugin.recipes.forEach { data ->
                            registry.registerRecipeFiller(data.recipeClass.java, { type -> type == data.info.type }) { recipe ->
                                ReiRecipe(data, data.crvrecipe.invoke(recipe))
                            }
                        }
                    }

                    @Suppress("UnstableApiUsage")
                    override fun registerBasicEntryFiltering(rule: BasicFilteringRule<*>) {
                        commonPlugin.plugin.removedItems.forEach {
                            rule.hide(EntryStack.of(VanillaEntryTypes.ITEM, it))
                        }
                    }

                    inner class ReiRecipe(val recipeData: ViewerInfo<*, *>, val rvRecipe: CRVRecipe<*>) : BasicDisplay(
                        rvRecipe.inputs.map { it.toRei() },
                        rvRecipe.outputs.map { it.toRei() },
                        Optional.of(rvRecipe.recipe.id)
                    ) {
                        override fun getCategoryIdentifier(): CategoryIdentifier<Display> = CategoryIdentifier.of(recipeData.info.id)
                    }

                    inner class ReiCategory(val recipeData: ViewerInfo<*, *>) : DisplayCategory<Display> {
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

                override fun provide(): Collection<REIClientPlugin> = plugin.provide()
                override fun getPluginProviderClass(): Class<REIClientPlugin> = plugin.pluginProviderClass
            }
        }
    }
}

fun CRVIngredient.toRei() : EntryIngredient = when {
    this.ingredient != null -> EntryIngredients.ofIngredient(this.ingredient)
    this.item != null -> EntryIngredients.of(this.item)
    this.fluid != null -> EntryIngredients.of(this.fluid)
    this.fluidTag != null -> EntryIngredients.ofFluidTag(this.fluidTag)
    else -> EntryIngredient.empty()
}