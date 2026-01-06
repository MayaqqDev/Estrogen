package dev.mayaqq.estrogen.compat.recipeviewers.api.emi

import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import dev.mayaqq.estrogen.content.blocks.RichCauldronInteraction
import dev.mayaqq.estrogen.id
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

class CauldronInteractionRecipe(val recipeCategory: EmiRecipeCategory, val item: Item, val interaction: RichCauldronInteraction) : EmiRecipe {
    override fun getCategory(): EmiRecipeCategory = recipeCategory

    override fun getId(): ResourceLocation = id("cauldron_interactions")

    override fun getInputs(): List<EmiIngredient> = listOf(EmiStack.of(item))

    override fun getOutputs(): List<EmiStack> = listOf(EmiStack.of(interaction.expectedOutput))

    override fun getDisplayWidth(): Int = 177

    override fun getDisplayHeight(): Int = 70

    override fun addWidgets(holder: WidgetHolder) {
        holder.addSlot(inputs.first(), 0, 0)
        holder.addSlot(outputs.first(), 20, 0)
    }
}