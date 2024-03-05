package dev.mayaqq.estrogen.fabric.integrations.emi.recipes;

import com.simibubi.create.compat.emi.recipes.CreateEmiRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import dev.mayaqq.estrogen.fabric.integrations.emi.EmiCompat;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class EntityInteractionEmiRecipe extends CreateEmiRecipe<EntityInteractionRecipe>  {
    public EntityInteractionEmiRecipe(EntityInteractionRecipe recipe) {
        super(EmiCompat.ENTITY_INTERACTION, recipe, 177, 70, (r) -> {
        });
        this.input = List.of(EmiIngredient.of(recipe.ingredient()));
        this.output = List.of(EmiStack.of(recipe.result()));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        addTexture(widgets, AllGuiTextures.JEI_SHADOW, 62, 47);
        addTexture(widgets, AllGuiTextures.JEI_DOWN_ARROW, 74, 10);

        ItemStack[] matchingStacks = recipe.entity().spawnEggs();

        SlotWidget eggs = addSlot(widgets, EmiIngredient.of(Ingredient.of(matchingStacks)), 27, 38);
        if (recipe.cantBeBaby()) {
            eggs.appendTooltip(Component.translatable("recipe.entity_interaction.cant_be_baby").withStyle(ChatFormatting.GOLD));
        }

        addSlot(widgets, input.get(0), 51, 5);

        addSlot(widgets, output.get(0), 132, 38).recipeContext(this);

        widgets.addDrawable(0, 0, 0, 0, (matrices, mouseX, mouseY, delta) -> {
            int item = (int) (System.currentTimeMillis() / 1000 % eggs.getStack().getEmiStacks().size());
            EmiIngredient current = eggs.getStack().getEmiStacks().get(item);

            ItemStack stack = current.getEmiStacks().get(0).getItemStack();
            LivingEntity entity = (LivingEntity) ((SpawnEggItem) stack.getItem()).getType(stack.getOrCreateTag()).create(Minecraft.getInstance().level);
            InventoryScreen.renderEntityInInventoryFollowsMouse(matrices, 88, 55, 20, (float) -mouseX + 87, (float) -mouseY + 20, entity);
        });
    }
}
