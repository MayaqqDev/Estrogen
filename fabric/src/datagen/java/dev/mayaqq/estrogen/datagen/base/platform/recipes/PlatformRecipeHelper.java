package dev.mayaqq.estrogen.datagen.base.platform.recipes;

import com.google.gson.JsonArray;
import dev.mayaqq.estrogen.datagen.base.platform.PlatformHelper;

public interface PlatformRecipeHelper extends PlatformHelper {
    long getFluidAmount(long amount);

    EstrogenLoadCondition isModLoaded(String modid);

    record EstrogenLoadCondition(String name, JsonArray condition) {}
}
