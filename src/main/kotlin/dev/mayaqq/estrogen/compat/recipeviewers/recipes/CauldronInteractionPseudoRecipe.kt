package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVPseudoRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.RecipeData
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.content.blocks.RichCauldronInteraction
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient

class CauldronInteractionPseudoRecipe(data: CIPRData) : CRVPseudoRecipe<CIPRData>(data) {
    override fun init() {
        addSlot(inputs.first(), 0, 0, Role.INPUT)
        addSlot(outputs.first(), 20, 0, Role.INPUT)
    }

    override val inputs: List<CRVIngredient>
        get() = listOf(CRVIngredient.of(Ingredient.of(data.item)))
    override val outputs: List<CRVIngredient>
        get() = listOf(CRVIngredient.of(Ingredient.of(data.interaction.expectedOutput)))
    override val catalysts: List<CRVIngredient> get() = listOf(CRVIngredient.of(Ingredient.of(Items.CAULDRON)))
}

class CIPRData(val item: Item, val interaction: RichCauldronInteraction) : RecipeData