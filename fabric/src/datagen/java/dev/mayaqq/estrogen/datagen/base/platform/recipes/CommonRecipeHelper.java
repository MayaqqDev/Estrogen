package dev.mayaqq.estrogen.datagen.base.platform.recipes;

import dev.mayaqq.estrogen.datagen.base.platform.ModPlatform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CommonRecipeHelper implements PlatformRecipeHelper {

    public static final CommonRecipeHelper INSTANCE = new CommonRecipeHelper();

    @Override
    public ModPlatform getPlatform() {
        return ModPlatform.COMMON;
    }

    @Override
    public String getName(String name) {
        return name + " (Common)";
    }

    @Override
    public TagKey<Item> getCommonTag(String name) {
        throw new UnsupportedOperationException("Common tags are not supported.");
    }

    @Override
    public long getFluidAmount(long amount) {
        throw new UnsupportedOperationException("Fluid amounts are not supported.");
    }

    @Override
    public EstrogenLoadCondition isModLoaded(String modid) {
        throw new UnsupportedOperationException("Mod loading conditions are not supported.");
    }
}
