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

                float[] rightColors = rightDye.getDyeColor().getTextureDiffuseColors();
                float[] leftColors = leftDye.getDyeColor().getTextureDiffuseColors();

                Color newPrimary, newSecondary;

                if(thighHighsItem.hasCustomColor(item)) {
                    Color oldPrimary = new Color(thighHighsItem.getColor(item, 0));
                    Color oldSecondary = new Color(thighHighsItem.getColor(item, 1));
                    newPrimary = multiplyColors(oldPrimary, leftColors);
                    newSecondary = multiplyColors(oldSecondary, rightColors);
                } else {
                    newPrimary = new Color(leftColors[0], leftColors[1], leftColors[2]);
                    newSecondary = new Color(rightColors[0], rightColors[1], rightColors[2]);
                }

                thighHighsItem.setColor(newThighHighsItem, newPrimary.getRGB(), newSecondary.getRGB());
                return newThighHighsItem;
            }
        }
        return ItemStack.EMPTY;
    }

    protected Color multiplyColors(Color oldColor, float[] dyeColor) {
        // Normalize the colors before multiplying
        float nR = oldColor.getRed() / 255f;
        float nG = oldColor.getGreen() / 255f;
        float nB = oldColor.getBlue() / 255f;
        float nDyeR = dyeColor[0] / 255f;
        float nDyeG = dyeColor[1] / 255f;
        float nDyeB = dyeColor[2] / 255f;

        return new Color(
            nR * nDyeR * 255,
            nG * nDyeG * 255,
            nB * nDyeB * 255
        );

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
