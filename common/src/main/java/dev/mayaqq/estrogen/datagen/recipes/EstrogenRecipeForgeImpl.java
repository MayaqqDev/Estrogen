package dev.mayaqq.estrogen.datagen.recipes;

import net.minecraft.resources.ResourceLocation;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

public class EstrogenRecipeForgeImpl implements EstrogenRecipeInterface {
    @Override
    public long getAmount(long amount) {
        double fabricBucket = 81000.0;
        double forgeBucket = 1000.0;
        // if the amount can be divided by 3, we change it to 1/4 on forge
        if (amount % 3 == 0) return amount > 27000 ? 500 : 250;
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
