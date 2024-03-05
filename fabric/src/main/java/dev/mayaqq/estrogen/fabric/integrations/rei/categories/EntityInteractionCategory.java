package dev.mayaqq.estrogen.fabric.integrations.rei.categories;

import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import dev.mayaqq.estrogen.registry.recipes.EntityInteractionRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.util.ClientEntryStacks;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class EntityInteractionCategory extends CreateRecipeCategory<EntityInteractionRecipe> {
    public EntityInteractionCategory(Info<EntityInteractionRecipe> info) {
        super(info);
    }

    Slot slot = null;

    public void addWidgets(CreateDisplay<EntityInteractionRecipe> display, List<Widget> ingredients, Point origin, Rectangle bounds) {
        Slot eggSlot = basicSlot( 27, 38, origin)
                .markInput()
                .entries(EntryIngredients.ofIngredient(Ingredient.of(display.getRecipe().entity().spawnEggs())));
        ingredients.add(eggSlot);
        slot = eggSlot;
        ClientEntryStacks.setTooltipProcessor(eggSlot.getCurrentEntry(), (entryStack, tooltip) -> {
            if (display.getRecipe().cantBeBaby())
                tooltip.add(Component.translatable("recipe.entity_interaction.cant_be_baby")
                        .withStyle(ChatFormatting.GOLD));
            return tooltip;
        });

        Slot slot = basicSlot(51, 5, origin)
                .markInput()
                .entries(EntryIngredients.ofIngredient(display.getRecipe().ingredient()));
        ingredients.add(slot);

        Slot outputSlot = basicSlot(132, 38, origin)
                .markOutput()
                .entries(display.getOutputEntries().get(0));

        ingredients.add(outputSlot);
    }

    @Override
    public void draw(EntityInteractionRecipe recipe, CreateDisplay<EntityInteractionRecipe> display, GuiGraphics graphics, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SLOT.render(graphics, 50, 4);
        AllGuiTextures.JEI_SLOT.render(graphics, 26, 37);
        getRenderedSlot(recipe, 0).render(graphics, 131, 37);
        AllGuiTextures.JEI_SHADOW.render(graphics, 62, 47);
        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, 74, 10);

        ItemStack stack = (ItemStack) slot.getCurrentEntry().getValue();
        LivingEntity entity = (LivingEntity) ((SpawnEggItem) stack.getItem()).getType(stack.getOrCreateTag()).create(Minecraft.getInstance().level);

        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, 88, 55, 20, (float) -mouseX + 87, (float) -mouseY + 20, entity);

    }
}
