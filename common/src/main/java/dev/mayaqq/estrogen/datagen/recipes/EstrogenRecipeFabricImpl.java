package dev.mayaqq.estrogen.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
}
