package dev.mayaqq.estrogen.compat.jei.recipes

import dev.mayaqq.estrogen.client.content.textures.RecipeTextures
import dev.mayaqq.estrogen.compat.jei.JeiSlot
import dev.mayaqq.estrogen.compat.jei.StackWIthCatalystJeiRenderable
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipe
import dev.mayaqq.estrogen.content.recipes.getSpawnEggs
import dev.mayaqq.estrogen.id
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
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.crafting.Ingredient


object EntityInteractionJeiRecipe : IRecipeCategory<EntityInteractionRecipe> {
    override fun getRecipeType(): RecipeType<EntityInteractionRecipe> = RecipeType(id("entity_interaction"), EntityInteractionRecipe::class.java)

    override fun getTitle(): Component = Component.translatable("estrogen.recipe.entity_interaction")

    override fun getBackground(): IDrawable {
        return object : IDrawable {
            override fun getWidth(): Int = EntityInteractionRecipe.width
            override fun getHeight(): Int = EntityInteractionRecipe.height
            override fun draw(graphics: GuiGraphics, xOffset: Int, yOffset: Int) {}
        }
    }

    override fun getIcon(): IDrawable = StackWIthCatalystJeiRenderable(EntityInteractionRecipe)

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: EntityInteractionRecipe, group: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 38)
            .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)
            .addIngredients(Ingredient.of(recipe.entity.getSpawnEggs().stream()))

        builder.addSlot(RecipeIngredientRole.INPUT, 51, 5)
            .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)
            .addIngredients(recipe.ingredient)

        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 38)
            .setBackground(JeiSlot(RecipeTextures.JEI_SLOT), -1, -1)
            .addItemStack(recipe.result)
    }

    override fun draw(
        recipe: EntityInteractionRecipe,
        iRecipeSlotsView: IRecipeSlotsView,
        graphics: GuiGraphics,
        mouseX: Double,
        mouseY: Double
    ) {
        RecipeTextures.JEI_SHADOW.render(graphics, 62, 47)
        RecipeTextures.JEI_DOWN_ARROW.render(graphics, 74, 10)

        val stack = iRecipeSlotsView.slotViews[0].displayedItemStack.get()
        val entity = (stack.item as SpawnEggItem).getType(stack.getOrCreateTag())
            .create(Minecraft.getInstance().level) as LivingEntity?
        InventoryScreen.renderEntityInInventoryFollowsMouse(
            graphics,
            88,
            55,
            20,
            -mouseX.toFloat() + 87,
            -mouseY.toFloat() + 20,
            entity
        )
    }
}