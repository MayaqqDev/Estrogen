package dev.mayaqq.estrogen.fabric.datagen.recipes;

import net.minecraft.resources.ResourceLocation;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

public class EstrogenRecipeFabricImpl implements EstrogenRecipeInterface {
    @Override
    public long getAmount(long amount) {
        return amount;
    }

    @Override
    public ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
        return new ResourceLocation(MOD_ID, ".fabric/" + identifier.getPath());
    }

    @Override
    public String getName(String name) {
        return name + " (Fabric)";
    }
}
