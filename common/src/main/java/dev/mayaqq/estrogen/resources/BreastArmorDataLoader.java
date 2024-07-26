package dev.mayaqq.estrogen.resources;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class BreastArmorDataLoader extends SimpleJsonResourceReloadListener {

    public static final BreastArmorDataLoader INSTANCE = new BreastArmorDataLoader();

    public final Map<ResourceLocation, BreastArmorData> data = new HashMap<>();

    public BreastArmorDataLoader() {
        super(new Gson(), "breast_armor_data");
    }


    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.data.clear();
        HashMap<ResourceLocation, BreastArmorData> newData = new HashMap<>();
        object.forEach((key, value) -> {
            newData.put(key, BreastArmorData.fromJson(value));
        });
        this.data.putAll(newData);
    }

    public BreastArmorData getData(ResourceLocation location) {
        return this.data.get(location);
    }
}
