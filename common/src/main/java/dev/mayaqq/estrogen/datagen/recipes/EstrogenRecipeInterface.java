package dev.mayaqq.estrogen.datagen.recipes;

import net.minecraft.resources.ResourceLocation;

public interface EstrogenRecipeInterface {
    long getAmount(long amount);

    ResourceLocation getRecipeIdentifier(ResourceLocation identifier);

    String getName(String name);
}
