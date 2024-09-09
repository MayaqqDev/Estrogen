package dev.mayaqq.estrogen.registry.recipes;

import com.simibubi.create.foundation.utility.Color;
import dev.mayaqq.estrogen.registry.EstrogenItems;
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

                if (rightToItem.getItem() instanceof DyeItem || leftToItem.getItem() instanceof DyeItem) {
                    for (int j = 0; j < 9; j++) {
                        if (j == leftSlot || j == middleSlot || j == rightSlot) {
                            continue;
                        }
                        if (!inv.getItem(j).isEmpty()) {
                            return false;
                        }
                    }

                    return EstrogenItems.THIGH_HIGHS.get().hasCustomColor(inv.getItem(middleSlot)) ||
                        (rightToItem.getItem() instanceof DyeItem && leftToItem.getItem() instanceof DyeItem);

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

                Color newPrimary, newSecondary;

                if(thighHighsItem.hasCustomColor(item)) {
                    Color oldPrimary = new Color(thighHighsItem.getColor(item, 0));
                    Color oldSecondary = new Color(thighHighsItem.getColor(item, 1));
                    newPrimary = mixColorWithDye(oldPrimary, leftToItem);
                    newSecondary = mixColorWithDye(oldSecondary, rightToItem);
                } else {
                    newPrimary = colorFromDye(leftToItem);
                    newSecondary = colorFromDye(rightToItem);
                }

                ItemStack newThighHighsItem = item.copy();
                thighHighsItem.setColor(newThighHighsItem, newPrimary.getRGB(), newSecondary.getRGB());
                return newThighHighsItem;
            }
        }
        return ItemStack.EMPTY;
    }

    private Color mixColorWithDye(Color original, ItemStack dyeStack) {
        if(dyeStack.isEmpty() || !(dyeStack.getItem() instanceof DyeItem)) return original;
        Color dyeColor = colorFromDye(dyeStack);
        return original.mixWith(dyeColor, .5f);
    }

    private Color colorFromDye(ItemStack dyeStack) {
        float[] dyeColors = ((DyeItem) dyeStack.getItem()).getDyeColor().getTextureDiffuseColors();
        return new Color(dyeColors[0], dyeColors[1], dyeColors[2], 1f);
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
