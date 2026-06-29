package dev.mayaqq.estrogen.compat.recipeviewers.recipes

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVIngredient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVRecipe
import dev.mayaqq.estrogen.compat.recipeviewers.api.Role
import dev.mayaqq.estrogen.compat.recipeviewers.api.ViewerInfo
import dev.mayaqq.estrogen.compat.recipeviewers.api.elements.GuiBlockRenderer
import dev.mayaqq.estrogen.content.recipes.SpongingRecipe
import invoke.kitty.kritter.utils.isLeft
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.RecipeHolder
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.Vec3

class SpongingCRVRecipe(recipe: RecipeHolder<SpongingRecipe>) : CRVRecipe<SpongingRecipe>(recipe) {
    override fun init() {
        addTexture(RecipeTextures.JEI_SHADOW, 62, 37)
        addTexture(RecipeTextures.JEI_ARROW, 7 + 18 + 4, 32)
        addTexture(RecipeTextures.JEI_ARROW, 152 - 42 - 4, 32)
        addSlot(inputs[0], 7, 28, Role.INPUT)
        addSlot(outputs[0], 152, 28, Role.OUTPUT)
        addDrawable(78, 42, GuiBlockRenderer(
            Blocks.SPONGE.defaultBlockState(),
            null,
            0, 0, 0,
            Vec3(22.5, 45.0, 0.0),
            20.0
        ))
    }

    override val inputs: List<CRVIngredient> get() = buildList {
        if (recipe.value().input.isLeft) {
            val fluidBlock = recipe.value().input.left!!
            add(CRVIngredient.of(fluidBlock))
        } else {
            add(CRVIngredient.of(recipe.value().input.right!!))
        }
    }

    override val outputs = listOf(CRVIngredient.of(BuiltInRegistries.FLUID.get(recipe.value().output)))

    override val catalysts = listOf(CRVIngredient.of(Items.SPONGE.defaultInstance))

    companion object : ViewerInfo<SpongingRecipe, SpongingCRVRecipe>(
        SpongingRecipe,
        { SpongingCRVRecipe(it as RecipeHolder<SpongingRecipe>) },
        SpongingRecipe::class
    )
}