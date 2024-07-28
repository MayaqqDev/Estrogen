package dev.mayaqq.estrogen.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class BreastArmorData {
    public ResourceLocation textureLocation;
    public ResourceLocation overlayLocation = null;
    public Pair<Integer, Integer> uv;
    public Pair<Float, Float> textureSize;

    public BreastArmorData(JsonElement jsonElement) {
        this.textureLocation = ResourceLocation.tryParse(GsonHelper.getAsString(jsonElement.getAsJsonObject(), "texture"));
        this.overlayLocation = jsonElement.getAsJsonObject().has("texture_overlay") ?
                ResourceLocation.tryParse(GsonHelper.getAsString(jsonElement.getAsJsonObject(), "texture_overlay")) : null;
        JsonArray uvArray = GsonHelper.getAsJsonArray(jsonElement.getAsJsonObject(), "uv");
        this.uv = Pair.of(uvArray.get(0).getAsInt(), uvArray.get(1).getAsInt());
        JsonArray textureSizeArray = GsonHelper.getAsJsonArray(jsonElement.getAsJsonObject(), "size");
        this.textureSize = Pair.of(textureSizeArray.get(0).getAsFloat(), textureSizeArray.get(1).getAsFloat());
    }
}
