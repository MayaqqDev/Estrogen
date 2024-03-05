package dev.mayaqq.estrogen.integrations.jei.categories;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;

public class EntityInteractionCategory extends CreateRecipeCategory<EntityInteractionRecipe> {
    public EntityInteractionCategory(Info<EntityInteractionRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EntityInteractionRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 27, 38)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(Ingredient.of(recipe.entity().spawnEggs()))
                .addTooltipCallback(
                        recipe.cantBeBaby()
                                ? (view, tooltip) -> tooltip.add(1, Component.translatable("recipe.entity_interaction.cant_be_baby")
                                .withStyle(ChatFormatting.GOLD))
                                : (view, tooltip) -> {}
                );

        builder.addSlot(RecipeIngredientRole.INPUT, 51, 5)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredients(recipe.ingredient());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 38)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStack(recipe.result());
    }

    @Override
    public void draw(EntityInteractionRecipe recipe, IRecipeSlotsView iRecipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(graphics, 62, 47);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 74, 10);

        ItemStack stack = iRecipeSlotsView.getSlotViews().get(0).getDisplayedItemStack().get();
        LivingEntity entity = (LivingEntity) ((SpawnEggItem) stack.getItem()).getType(stack.getOrCreateTag()).create(Minecraft.getInstance().level);
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, 88, 55, 20, (float) -mouseX + 87, (float) -mouseY + 20, entity);
    }
}
