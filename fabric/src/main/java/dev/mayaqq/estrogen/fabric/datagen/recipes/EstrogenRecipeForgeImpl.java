package dev.mayaqq.estrogen.fabric.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EstrogenRecipeForgeImpl implements EstrogenRecipeInterface {
    @Override
    public long getAmount(long amount) {
        double fabricBucket = 81000.0;
        double forgeBucket = 1000.0;
        if (amount == 27000) return 250;
        if (amount == 54000) return 500;
        return ((long) ((amount / fabricBucket) * forgeBucket));
    }

    @Override
    public String getName(String name) {
        return name + " (Forge)";
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

    @Override
    public TagKey<Item> getCommonTag(String name) {
        if (name.equals("copper_plates")) return TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("forge", "plates/copper"));
        if (name.equals("zinc_nuggets")) return TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("forge", "nuggets/zinc"));
        return TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("forge", name));
    }
}
