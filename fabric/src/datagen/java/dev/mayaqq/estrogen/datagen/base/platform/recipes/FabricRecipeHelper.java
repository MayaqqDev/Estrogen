package dev.mayaqq.estrogen.datagen.base.platform.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.mayaqq.estrogen.datagen.base.platform.ModPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FabricRecipeHelper implements PlatformRecipeHelper {

    public static final FabricRecipeHelper INSTANCE = new FabricRecipeHelper();

    @Override
    public ModPlatform getPlatform() {
        return ModPlatform.FABRIC;
    }

    @Override
    public String getName(String name) {
        return name + " (Fabric)";
    }

    @Override
    public TagKey<Item> getCommonTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
    }

    @Override
    public long getFluidAmount(long amount) {
        return amount;
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
