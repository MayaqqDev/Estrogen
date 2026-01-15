package dev.mayaqq.estrogen.compat.recipeviewers.api.jei

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.text.Text
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPseudoRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.CommonRecipeViewer
import dev.mayaqq.estrogen.compat.recipeviewers.api.PseudoRecipeHolder
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.utils.exceptions.EmptyTagException
import mezz.jei.api.IModPlugin
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import mezz.jei.api.runtime.IJeiRuntime
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.material.Fluid

object JeiPluginRegister {

    val crvPlugins by lazy { CommonRecipeViewer.getPlugins() }
    val recipeTypes by lazy { buildMap {
        crvPlugins.forEach { plugin ->
            plugin.plugin.recipes.forEach { recipe ->
                put(recipe.info.type, RecipeType(recipe.info.id, recipe.recipeClass.java) as RecipeType<Any>) }
        }
    } }

    val pseudoRecipeTypes by lazy { buildMap {
        crvPlugins.forEach { (plugin, modid) ->
            plugin.pseudoRecipes.forEach { pseudoRecipe ->
                put(pseudoRecipe, RecipeType(pseudoRecipe.id, CRVPseudoRecipe::class.java))
            }
        }
    }
    }

    val recipeInstances = mutableMapOf<Recipe<*>, CRVRecipe<*>>()
    val pseudoRecipeInstances = mutableListOf<CRVPseudoRecipe<*>>()

    fun getPlugins(): List<IModPlugin> {
        return CommonRecipeViewer.getPlugins().map { commonPlugin ->
            object : IModPlugin {
                override fun getPluginUid(): ResourceLocation = ResourceLocation(commonPlugin.modid, "jei_plugin")

                override fun registerCategories(registry: IRecipeCategoryRegistration) {
                    commonPlugin.plugin.pseudoRecipes.forEach { pseudoRecipe ->
                        registry.addRecipeCategories(object : IRecipeCategory<Any> {
                            override fun getRecipeType(): RecipeType<Any> = pseudoRecipeTypes[pseudoRecipe] as RecipeType<Any>

                            override fun getTitle(): Component = Text.translatable("${pseudoRecipe.id.namespace}.recipe.${pseudoRecipe.id.path}")

                            override fun getHeight(): Int = pseudoRecipe.height
                            override fun getWidth(): Int = pseudoRecipe.width

                            override fun getIcon(): IDrawable = object : IDrawable {
                                override fun getWidth(): Int = 16
                                override fun getHeight(): Int = 16
                                override fun draw(
                                    graphics: GuiGraphics,
                                    offsetX: Int,
                                    offsetY: Int
                                ) {
                                    pseudoRecipe.render(graphics, offsetX, offsetY, 0F)
                                }
                            }
                            override fun setRecipe(layout: IRecipeLayoutBuilder, recipe: Any, group: IFocusGroup) {
                                val actualRecipe = recipe as CRVPseudoRecipe<*>
                                if (!pseudoRecipeInstances.contains(actualRecipe)) {
                                    pseudoRecipeInstances.add(actualRecipe)
                                    actualRecipe.init()
                                }
                                actualRecipe.slots.forEach { slot ->
                                    val jeiSlot = layout.addSlot(slot.role.toJei(), slot.x, slot.y)
                                        .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)

                                    if (slot.ingredient.ingredient != null) jeiSlot.addIngredients(slot.ingredient.ingredient!!)
                                    else if (slot.ingredient.item != null) jeiSlot.addItemStack(slot.ingredient.item!!)
                                    else if (slot.ingredient.fluid != null) jeiSlot.addFluidStack(slot.ingredient.fluid!!, 1000L)
                                    else if (slot.ingredient.fluidTag != null) jeiSlot.addFluidStack(slot.ingredient.fluidTag!!.first, 1000L)
                                }
                            }

                            override fun draw(recipe: Any, slotView: IRecipeSlotsView, graphics: GuiGraphics, mouseX: Double, mouseY: Double) {
                                val actualRecipe = recipe as CRVPseudoRecipe<*>
                                if (!pseudoRecipeInstances.contains(actualRecipe)) {
                                    pseudoRecipeInstances.add(actualRecipe)
                                    actualRecipe.init()
                                }
                                actualRecipe!!.textures.forEach { texture ->
                                    texture.coorded.render(graphics, texture.x, texture.y)
                                }
                                actualRecipe.drawables.forEach { drawable ->
                                    graphics.pushPop {
                                        translate(drawable.x.toFloat(), drawable.y.toFloat(), 0.0F)
                                        drawable.coorded.draw(
                                            graphics,
                                            0,
                                            0,
                                            mouseX.toInt(),
                                            mouseY.toInt(),
                                            0F
                                        )
                                    }
                                }
                            }

                        })
                    }
                    commonPlugin.plugin.recipes.forEach { info ->
                        registry.addRecipeCategories(object : IRecipeCategory<Any> {
                            override fun getRecipeType(): RecipeType<Any> = recipeTypes[info.info.type]!!

                            override fun getTitle(): Component = Text.translatable("${info.info.id.namespace}.recipe.${info.info.id.path}")

                            override fun getHeight(): Int = info.info.height
                            override fun getWidth(): Int = info.info.width

                            override fun getIcon(): IDrawable = object : IDrawable {
                                override fun getWidth(): Int = 16

                                override fun getHeight(): Int = 16

                                override fun draw(graphics: GuiGraphics, offsetX: Int, offsetY: Int) {
                                    info.info.render(graphics, offsetX, offsetY, 0F)
                                }
                            }

                            override fun setRecipe(layout: IRecipeLayoutBuilder, recipe: Any, group: IFocusGroup) {
                                val actualRecipe = recipe as Recipe<*>
                                if (!recipeInstances.contains(actualRecipe)) {
                                    recipeInstances[actualRecipe] = info.crvrecipe.invoke(actualRecipe)
                                    recipeInstances[actualRecipe]!!.init()
                                }
                                val rvRecipe = recipeInstances[actualRecipe]!!
                                rvRecipe.slots.forEach { slot ->
                                    val jeiSlot = layout.addSlot(slot.role.toJei(), slot.x, slot.y)
                                        .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)

                                    if (slot.ingredient.ingredient != null) jeiSlot.addIngredients(slot.ingredient.ingredient!!)
                                    else if (slot.ingredient.item != null) jeiSlot.addItemStack(slot.ingredient.item!!)
                                    else if (slot.ingredient.fluid != null) jeiSlot.addFluidStack(slot.ingredient.fluid!!, 1000L)
                                    else if (slot.ingredient.fluidTag != null) jeiSlot.addFluidStack(slot.ingredient.fluidTag!!.first, 1000L)
                                }
                            }

                            override fun draw(recipe: Any, slotView: IRecipeSlotsView, graphics: GuiGraphics, mouseX: Double, mouseY: Double) {
                                val actualRecipe = recipe as Recipe<*>
                                if (!recipeInstances.contains(actualRecipe)) {
                                    recipeInstances[actualRecipe] = info.crvrecipe.invoke(actualRecipe)
                                    recipeInstances[actualRecipe]!!.init()
                                }
                                val rvRecipe = recipeInstances[actualRecipe]
                                rvRecipe!!.textures.forEach { texture ->
                                    texture.coorded.render(graphics, texture.x, texture.y)
                                }
                                rvRecipe.drawables.forEach { drawable ->
                                    graphics.pushPop {
                                        translate(drawable.x.toFloat(), drawable.y.toFloat(), 0.0F)
                                        drawable.coorded.draw(
                                            graphics,
                                            0,
                                            0,
                                            mouseX.toInt(),
                                            mouseY.toInt(),
                                            0F
                                        )
                                    }
                                }
                            }

                        })
                    }
                }

                override fun registerRecipes(registry: IRecipeRegistration) {
                    recipeTypes.forEach { (type, jeiType) ->
                        registry.addRecipes(jeiType, Minecraft.getInstance().level?.recipeManager?.recipes?.filter { it.type == type }?: return@forEach)
                    }

                        pseudoRecipeTypes.forEach { (recipe, type) ->
                            val list = buildList {
                                recipe.dataSupplier.invoke().forEach { data ->
                                    val method = recipe.builder::class.java.getMethod("invoke", Object::class.java)
                                    method.isAccessible = true
                                    add(method.invoke(recipe.builder, data) as CRVPseudoRecipe<*>)
                                }
                            }
                            registry.addRecipes(type, list)
                        }

                    // Hiding
                    commonPlugin.plugin.removedItems.forEach {
                        registry.ingredientManager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, listOf(it))
                    }
                }

                override fun onRuntimeAvailable(runtime: IJeiRuntime) {
                    commonPlugin.plugin.removedRecipes.forEach { recipe ->
                        runtime.jeiHelpers.allRecipeTypes.forEach { type ->
                            if (type.uid == ResourceLocation("minecraft", "crafting")) {
                                runtime.recipeManager.javaClass.getDeclaredMethod("hideRecipes",
                                    RecipeType::class.java,
                                    Collection::class.java
                                ).invoke(
                                    runtime.recipeManager,
                                    type,
                                    runtime.recipeManager.createRecipeLookup(type).get().filter { (it as Recipe<*>).id == recipe }.toList()
                                )
                            }
                        }
                    }
                }

                inner class JeiSlot(val texture: RecipeTextures) : IDrawable {
                    override fun getWidth(): Int = texture.width
                    override fun getHeight(): Int = texture.height
                    override fun draw(graphics: GuiGraphics, xOffset: Int, yOffset: Int) = texture.render(graphics, xOffset, yOffset)
                }
            }
        }
    }
}

fun Role.toJei(): RecipeIngredientRole = when(this) {
    Role.INPUT -> RecipeIngredientRole.INPUT
    Role.OUTPUT -> RecipeIngredientRole.OUTPUT
    Role.CATALYST -> RecipeIngredientRole.CATALYST
    Role.RENDER_ONLY -> RecipeIngredientRole.RENDER_ONLY
}

val TagKey<Fluid>.first: Fluid
    get() = buildList { BuiltInRegistries.FLUID.getTagOrEmpty(this@first).forEach {add(it)} }.getOrElse(0, throw EmptyTagException(this)).value()