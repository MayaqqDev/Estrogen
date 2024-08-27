package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;

public record Cosmetic(String id, String name, CosmeticTexture texture, String model) {

    public static Cosmetic fromJson(String id, JsonObject json) {
        String name = GsonHelper.getAsString(json, "name", id);
        CosmeticTexture texture = new CosmeticTexture(json.get("texture").getAsString());
        CosmeticGeoModel model = new CosmeticGeoModel(json.get("model").getAsString());

        return new Cosmetic(
                id,
                name,
                texture,
                new CosmeticModel(model, texture)
        );
    }
}
