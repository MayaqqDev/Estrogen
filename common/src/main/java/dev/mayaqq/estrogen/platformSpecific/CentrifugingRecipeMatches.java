package dev.mayaqq.estrogen.platformSpecific;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.mayaqq.estrogen.registry.common.recipes.CentrifugingRecipe;
import dev.mayaqq.estrogen.registry.common.recipes.common.DataInventory;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;

public class CentrifugingRecipeMatches {

    private CentrifugingRecipeMatches() {}

    @Contract
    @ExpectPlatform
    public static boolean matches(DataInventory inventory, Level world, CentrifugingRecipe recipe) {
        throw new UnsupportedOperationException();
    }
}
