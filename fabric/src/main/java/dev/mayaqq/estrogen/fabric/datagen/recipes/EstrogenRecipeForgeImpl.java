package dev.mayaqq.estrogen.fabric.datagen.recipes;

import net.minecraft.resources.ResourceLocation;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

public class EstrogenRecipeForgeImpl implements EstrogenRecipeInterface {
    @Override
    public long getAmount(long amount) {
        double fabricBucket = 108000.0;
        double forgeBucket = 1000.0;
        return ((long) ((amount / fabricBucket) * forgeBucket));
    }

    @Override
    public ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
        return new ResourceLocation(MOD_ID, ".forge/" + identifier.getPath());
    }

    @Override
    public String getName(String name) {
        return name + " (Forge)";
    }
}
