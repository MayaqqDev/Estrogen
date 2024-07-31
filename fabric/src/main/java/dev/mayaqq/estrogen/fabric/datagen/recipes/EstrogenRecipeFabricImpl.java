package dev.mayaqq.estrogen.fabric.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EstrogenRecipeFabricImpl implements EstrogenRecipeInterface {
    @Override
    public long getAmount(long amount) {
        return amount;
    }

    @Override
    public String getName(String name) {
        return name + " (Fabric)";
    }

    @Override
    public EstrogenLoadCondition isModLoaded(String modid) {
        JsonArray array = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("condition", "fabric:all_mods_loaded");
        JsonArray values = new JsonArray();
        values.add(modid);
        object.add("values", values);
        array.add(object);
        return new EstrogenLoadCondition("fabric:load_conditions", array);
    }

    @Override
    public TagKey<Item> getCommonTag(String name) {
        return TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("c", name));
    }
}
