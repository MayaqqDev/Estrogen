package dev.mayaqq.estrogen.fabric.datagen.recipes.create;

import net.minecraft.resources.ResourceLocation;

public interface EstrogenRecipeInterface {
    long getAmount(long amount);
    ResourceLocation getRecipeIdentifier(ResourceLocation identifier);
    String getName(String name);
}
