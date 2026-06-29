package dev.mayaqq.estrogen.compat.recipeviewers.api.rei

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.*
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
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeHolder
import java.util.*

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
                    override fun getPluginProviderName(): String = identifier(commonPlugin.modid, "rei_client").toString()

                    override fun registerCategories(registry: CategoryRegistry) {
                        commonPlugin.plugin.recipes.forEach { data ->
                            val category = ReiCategory(data)
                            registry.add(category)
                            data.info.workstation?.let {
                                registry.addWorkstations(category.categoryIdentifier, EntryStacks.of(data.info.workstation))
                            }
                        }

                        commonPlugin.plugin.pseudoRecipes?.forEach { pseudoRecipeHolder ->
                            registry.add(ReiPseudoCategory(pseudoRecipeHolder))
                        }
                    }

                    override fun registerDisplays(registry: DisplayRegistry) {
                        commonPlugin.plugin.recipes.forEach { data ->
                            registry.registerRecipeFiller(data.recipeClass.java, { type -> type == data.info.type }) { recipe ->
                                ReiRecipe(data, data.crvrecipe.invoke(recipe as RecipeHolder<Recipe<*>>).apply { init() })
                            }
                        }
                        commonPlugin.plugin.pseudoRecipes?.forEach { recipe ->
                            registry.registerFiller(RecipeData::class.java) { data ->
                                val data = data as RecipeData
                                val method = recipe.builder::class.java.getMethod("invoke", Object::class.java)
                                method.isAccessible = true
                                ReiPseudoRecipe(recipe, (method.invoke(recipe.builder, data) as CRVPseudoRecipe<*>).apply { init() })
                            }
                            recipe.dataSupplier.invoke().forEach(registry::add)
                        }
                    }

                    @Suppress("UnstableApiUsage")
                    override fun registerBasicEntryFiltering(rule: BasicFilteringRule<*>) {
                        commonPlugin.plugin.removedItems.forEach {
                            rule.hide(EntryStack.of(VanillaEntryTypes.ITEM, it))
                        }
                    }

                    inner class ReiPseudoRecipe(val recipeData: PseudoRecipeHolder<*>, val rvRecipe: CRVPseudoRecipe<*>) : BasicDisplay(
                        rvRecipe.inputs.map { it.toRei() },
                        rvRecipe.outputs.map { it.toRei() },
                        Optional.of(rvRecipe.getId())
                    ) {
                        override fun getCategoryIdentifier(): CategoryIdentifier<Display> = CategoryIdentifier.of(recipeData.id)
                    }

                    inner class ReiPseudoCategory(val recipe: PseudoRecipeHolder<*>) : DisplayCategory<Display> {
                        override fun getCategoryIdentifier(): CategoryIdentifier<out Display> = CategoryIdentifier.of(recipe.id)

                        override fun getTitle(): Component = Text.translatable("${recipe.id.namespace}.recipe.${recipe.id.path}")

                        override fun getIcon(): Renderer = Renderer { graphics, bounds, mouseX, mouseY, partialTick ->
                            recipe.render(graphics, bounds.centerX - 9, bounds.centerY - 9, partialTick)
                        }

                        override fun getDisplayHeight(): Int = recipe.height
                        override fun getDisplayWidth(display: Display): Int = recipe.width

                        override fun setupDisplay(display: Display, bounds: Rectangle): List<Widget> = buildList {
                            val display = display as ReiPseudoRecipe
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
                                        translate(bounds.getX().toDouble() + drawable.x, bounds.getY().toDouble() + 4 + drawable.y, 0.0)
                                        drawable.coorded.draw(graphics, 0, 0, mouseX, mouseY, delta)
                                    }
                                })
                            }
                            display.rvRecipe.slots.forEach { slot ->
                                val reiSlot = Widgets.createSlot(Point(bounds.x + slot.x, bounds.y + slot.y + 4)).disableBackground()
                                reiSlot.entries(slot.ingredient.toRei())
                                when (slot.role) {
                                    Role.INPUT -> reiSlot.markInput()
                                    Role.OUTPUT -> reiSlot.markOutput()
                                    Role.CATALYST -> {}
                                    Role.RENDER_ONLY -> {}
                                }

                                add(Widgets.createDrawableWidget { graphics, mouseX, mouseY, delta ->
                                    graphics.pushPop {
                                        translate(bounds.getX().toDouble(), bounds.getY().toDouble() + 4, 0.0)
                                        RecipeTextures.JEI_SLOT.render(graphics, slot.x - 1, slot.y - 1)
                                    }
                                })
                                add(reiSlot)
                            }
                        }
                    }

                    inner class ReiRecipe(val recipeData: ViewerInfo<*, *>, val rvRecipe: CRVRecipe<*>) : BasicDisplay(
                        rvRecipe.inputs.map { it.toRei() },
                        rvRecipe.outputs.map { it.toRei() },
                        Optional.of(rvRecipe.recipe.id())
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
                                        translate(bounds.getX().toDouble() + drawable.x, bounds.getY().toDouble() + 4 + drawable.y, 0.0)
                                        drawable.coorded.draw(graphics, 0, 0, mouseX, mouseY, delta)
                                    }
                                })
                            }
                            display.rvRecipe.slots.forEach { slot ->
                                val reiSlot = Widgets.createSlot(Point(bounds.x + slot.x, bounds.y + slot.y + 4)).disableBackground()
                                reiSlot.entries(slot.ingredient.toRei())
                                when (slot.role) {
                                    Role.INPUT -> reiSlot.markInput()
                                    Role.OUTPUT -> reiSlot.markOutput()
                                    Role.CATALYST -> {}
                                    Role.RENDER_ONLY -> {}
                                }

                                add(Widgets.createDrawableWidget { graphics, mouseX, mouseY, delta ->
                                    graphics.pushPop {
                                        translate(bounds.getX().toDouble(), bounds.getY().toDouble() + 4, 0.0)
                                        RecipeTextures.JEI_SLOT.render(graphics, slot.x - 1, slot.y - 1)
                                    }
                                })
                                add(reiSlot)
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