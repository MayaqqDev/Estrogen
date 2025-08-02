package dev.mayaqq.estrogen.compat.jei.recipes

import dev.mayaqq.cynosure.utils.isLeft
import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.jei.JeiSlot
import dev.mayaqq.estrogen.compat.jei.StackWIthCatalystJeiRenderable
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.SpongingRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.exceptions.EmptyTagException
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.internal.throwMissingFieldException
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.gui.ingredient.IRecipeSlotsView
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.material.Fluid

//TODO: FIx up the display and things
object SpongingJeiRecipe : IRecipeCategory<SpongingRecipe> {
    override fun getRecipeType(): RecipeType<SpongingRecipe> = RecipeType(id("sponging"), SpongingRecipe::class.java)

    override fun getTitle(): Component = Component.translatable("estrogen.recipe.sponging")

    override fun getBackground(): IDrawable {
        return object : IDrawable {
            override fun getWidth(): Int = SpongingRecipe.width
            override fun getHeight(): Int = SpongingRecipe.height
            override fun draw(graphics: GuiGraphics, xOffset: Int, yOffset: Int) {}
        }
    }

    override fun getIcon(): IDrawable = StackWIthCatalystJeiRenderable(SpongingRecipe)

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: SpongingRecipe, group: IFocusGroup) {
        val fluid: Fluid = if (recipe.input.isLeft) {
            BuiltInRegistries.FLUID.get(BuiltInRegistries.BLOCK.getKey(recipe.input.left!!))
        } else {
            recipe.input.right!!.first
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 38)
            .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)
            .addFluidStack(fluid, 1000L)

        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 38)
            .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)
            .addFluidStack(BuiltInRegistries.FLUID.get(recipe.output), 1000L)
    }

    override fun draw(
        recipe: SpongingRecipe,
        iRecipeSlotsView: IRecipeSlotsView,
        graphics: GuiGraphics,
        mouseX: Double,
        mouseY: Double
    ) {
        RecipeTextures.JEI_SHADOW.render(graphics, 62, 47)
        RecipeTextures.JEI_DOWN_ARROW.render(graphics, 74, 10)
    }

    val TagKey<Fluid>.first: Fluid
        get() = buildList { BuiltInRegistries.FLUID.getTagOrEmpty(this@first).forEach {add(it)} }.getOrElse(0, throw EmptyTagException(this)).value()
}