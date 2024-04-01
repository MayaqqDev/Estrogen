package dev.mayaqq.estrogen.datagen.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

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
    public ResourceLocation getRecipeIdentifier(ResourceLocation identifier) {
        return new ResourceLocation(MOD_ID, ".forge/" + identifier.getPath());
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
}
