package dev.mayaqq.estrogen.registry.recipes;

import dev.mayaqq.estrogen.registry.EstrogenRecipes;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.awt.*;

public class ThighHighDyeRecipe extends CustomRecipe {
    public ThighHighDyeRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        for (int i = 0; i < 3; i++) {
            int leftSlot = i * 3;
            int middleSlot = leftSlot + 1;
            int rightSlot = leftSlot + 2;
            if (inv.getItem(middleSlot).getItem() instanceof ThighHighsItem) {
                ItemStack rightToItem = inv.getItem(rightSlot);
                ItemStack leftToItem = inv.getItem(leftSlot);

                if (rightToItem.getItem() instanceof DyeItem && leftToItem.getItem() instanceof DyeItem) {
                    for (int j = 0; j < 9; j++) {
                        if (j == leftSlot || j == middleSlot || j == rightSlot) {
                            continue;
                        }
                        if (!inv.getItem(j).isEmpty()) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        for (int i = 0; i < 3; i++) {
            int leftSlot = i * 3;
            int middleSlot = leftSlot + 1;
            int rightSlot = leftSlot + 2;
            if (inv.getItem(middleSlot).getItem() instanceof ThighHighsItem thighHighsItem) {
                ItemStack item = inv.getItem(middleSlot);
                ItemStack leftToItem = inv.getItem(leftSlot);
                ItemStack rightToItem = inv.getItem(rightSlot);

                DyeItem rightDye = (DyeItem) rightToItem.getItem();
                DyeItem leftDye = (DyeItem) leftToItem.getItem();

                ItemStack newThighHighsItem = item.copy();
                float[] rightColor = rightDye.getDyeColor().getTextureDiffuseColors();
                float[] leftColor = leftDye.getDyeColor().getTextureDiffuseColors();
                thighHighsItem.setColor(newThighHighsItem, new Color(leftColor[0], leftColor[1], leftColor[2]).getRGB(), new Color(rightColor[0], rightColor[1], rightColor[2]).getRGB());
                return newThighHighsItem;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height == 9;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return EstrogenRecipes.THIGH_HIGH_DYE_SERIALIZER.get();
    }
}
