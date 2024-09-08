package dev.mayaqq.estrogen.datagen.base.platform.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.mayaqq.estrogen.datagen.base.platform.ModPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeRecipeHelper implements PlatformRecipeHelper {

    public static final ForgeRecipeHelper INSTANCE = new ForgeRecipeHelper();

    @Override
    public ModPlatform getPlatform() {
        return ModPlatform.FORGE;
    }

    @Override
    public String getName(String name) {
        return name + " (Forge)";
    }

    @Override
    public TagKey<Item> getCommonTag(String name) {
        name = switch (name) {
            case "copper_plates" -> "plates/copper";
            case "zinc_nuggets" -> "nuggets/zinc";
            default -> name;
        };
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", name));
    }

    @Override
    public long getFluidAmount(long amount) {
        double fabricBucket = 81000.0;
        double forgeBucket = 1000.0;
        if (amount == 27000) return 250;
        if (amount == 54000) return 500;
        return ((long) ((amount / fabricBucket) * forgeBucket));
    }

    @Override
    public EstrogenLoadCondition isModLoaded(String modid) {
        JsonArray conditions = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("type", "forge:mod_loaded");
        object.addProperty("modid", modid);
        conditions.add(object);
        return new EstrogenLoadCondition("conditions", conditions);
    }
}
