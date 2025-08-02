package dev.mayaqq.estrogen.compat.rei.recipes

import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.utils.isLeft
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.rei.StackWithCatalystReiRenderable
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.SpongingRecipe
import dev.mayaqq.estrogen.content.recipes.viewers.RecipeViewerInfo
import dev.mayaqq.estrogen.id
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.client.gui.Renderer
import me.shedaniel.rei.api.client.gui.widgets.Slot
import me.shedaniel.rei.api.client.gui.widgets.Widget
import me.shedaniel.rei.api.client.gui.widgets.Widgets
import me.shedaniel.rei.api.client.registry.display.DisplayCategory
import me.shedaniel.rei.api.common.category.CategoryIdentifier
import me.shedaniel.rei.api.common.display.basic.BasicDisplay
import me.shedaniel.rei.api.common.util.EntryIngredients
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import java.util.*

//TODO: Spice it up :3
class SpongingReiRecipe(val recipe: SpongingRecipe) : BasicDisplay(
listOf(if (recipe.input.isLeft)
    EntryIngredients.of(BuiltInRegistries.FLUID.get(BuiltInRegistries.BLOCK.getKey(recipe.input.left!!))) else
    EntryIngredients.ofFluidTag(recipe.input.right!!)),
    listOf(EntryIngredients.of(BuiltInRegistries.FLUID.get(recipe.output))),
    Optional.of(recipe.id)
) {
    companion object {
        val category: CategoryIdentifier<SpongingReiRecipe> = CategoryIdentifier.of(id("sponging"))
    }

    override fun getCategoryIdentifier(): CategoryIdentifier<*> = category

    class Category(private val viewerInfo: RecipeViewerInfo) : DisplayCategory<SpongingReiRecipe> {
        override fun getCategoryIdentifier(): CategoryIdentifier<out SpongingReiRecipe> = category

        override fun getTitle(): Component = Component.translatable("estrogen.recipe.sponging")

        override fun getIcon(): Renderer = StackWithCatalystReiRenderable(viewerInfo)

        override fun getDisplayHeight(): Int = EntityInteractionRecipe.height
        override fun getDisplayWidth(recipe: SpongingReiRecipe): Int = EntityInteractionRecipe.width

        override fun setupDisplay(recipe: SpongingReiRecipe, bounds: Rectangle): MutableList<Widget> {
            val widgets = mutableListOf<Widget>()

            widgets.add(Widgets.createRecipeBase(bounds))

            val inputSlot: Slot = Widgets.createSlot(Point(bounds.x + 51, bounds.y + 5 + 4))
                .markInput()
                .disableBackground()
                .entries(recipe.inputs[0])
            widgets.add(inputSlot)

            val outputSlot: Slot = Widgets.createSlot(Point(bounds.x + 132, bounds.y + 38 + 4))
                .markOutput()
                .disableBackground()
                .entries(recipe.outputs[0])
            widgets.add(outputSlot)

            widgets.add(Widgets.createDrawableWidget { graphics, mouseX, mouseY, delta ->
                graphics.pushPop {
                    translate(bounds.getX().toDouble(), bounds.getY().toDouble() + 4, 0.0)
                    RecipeTextures.JEI_SLOT.render(graphics, 50, 4)
                    RecipeTextures.JEI_SLOT.render(graphics, 26, 37)
                    RecipeTextures.JEI_SLOT.render(graphics, 131, 37)
                    RecipeTextures.JEI_SHADOW.render(graphics, 62, 47)
                    RecipeTextures.JEI_DOWN_ARROW.render(graphics, 74, 10)
                }
            })

            return widgets
        }
    }
}