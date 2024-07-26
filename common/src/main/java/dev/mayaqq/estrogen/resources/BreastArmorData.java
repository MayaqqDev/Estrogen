package dev.mayaqq.estrogen.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public record BreastArmorData(ResourceLocation textureLocation, Pair<Integer, Integer> uv) {
    public static BreastArmorData fromJson(JsonElement jsonElement) {
        var textureLocation = ResourceLocation.tryParse(GsonHelper.getAsString(jsonElement.getAsJsonObject(), "texture"));
        JsonArray uvArray = GsonHelper.getAsJsonArray(jsonElement.getAsJsonObject(), "uv");
        Pair<Integer, Integer> uv = Pair.of(uvArray.get(0).getAsInt(), uvArray.get(1).getAsInt());
        return new BreastArmorData(textureLocation, uv);
    }
}
