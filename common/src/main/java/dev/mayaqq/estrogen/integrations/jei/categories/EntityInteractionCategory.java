package dev.mayaqq.estrogen.integrations.jei.categories;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import net.minecraft.world.entity.LivingEntity;

public class EntityInteractionCategory extends CreateRecipeCategory<EntityInteractionRecipe> {
    public EntityInteractionCategory(Info<EntityInteractionRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EntityInteractionRecipe recipe, IFocusGroup focuses) {
        IRecipeSlotBuilder slotBuilder1 = builder.addSlot(RecipeIngredientRole.INPUT, 27, 32);
        slotBuilder1.setBackground(getRenderedSlot(), -1, -1);
        slotBuilder1.addIngredients(recipe.ingredient());

        IRecipeSlotBuilder slotBuilder2 = builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 51);
        slotBuilder2.setBackground(getRenderedSlot(), -1, -1);
        slotBuilder2.addItemStack(recipe.result());
    }

    @Override
    public void draw(EntityInteractionRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 126, 29);

        LivingEntity entity = (LivingEntity) recipe.entity().getCycling().create(Minecraft.getInstance().level);
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, 100, 20, 20, (float) -mouseX, (float) -mouseY, entity);
    }
}
