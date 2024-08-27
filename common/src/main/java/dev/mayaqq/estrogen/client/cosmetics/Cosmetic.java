package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonObject;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import net.minecraft.util.GsonHelper;

import java.util.Optional;

public record Cosmetic(String id, String name, CosmeticTexture texture, CosmeticModel model) {

    public static Cosmetic fromJson(String id, JsonObject json) {
        String name = GsonHelper.getAsString(json, "name", id);
        CosmeticTexture texture = new CosmeticTexture(json.get("texture").getAsString());
        CosmeticModel model = new CosmeticModel(json.get("model").getAsString());

        return new Cosmetic(
                id,
                name,
                texture,
                model
        );
    }

    public Optional<SuperByteBuffer> cachedRender() {
        return model.get().map(baked ->
            CreateClient.BUFFER_CACHE.get(EstrogenRenderer.COSMETICS, this, baked::makeBuffer)
        );
    }
}
