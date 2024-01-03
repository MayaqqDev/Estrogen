package dev.mayaqq.estrogen.platformSpecific;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import dev.mayaqq.estrogen.registry.common.recipes.CentrifugingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;

public class CentrifugingRecipeMatches {

    private CentrifugingRecipeMatches() {}

    @Contract
    @ExpectPlatform
    public static boolean matches(CentrifugeBlockEntity blockEntity, Level world, CentrifugingRecipe recipe) {
        throw new UnsupportedOperationException();
    }
}
