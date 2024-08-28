package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import java.util.function.Function;

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

    public void render(Function<ResourceLocation, RenderType> renderType, MultiBufferSource source, PoseStack matrices, int light) {
        model.get().ifPresent(model -> model.renderInto(
            source.getBuffer(renderType.apply(texture.getResourceLocation())),
            matrices.last(),
            0xFFFFFFFF,
            light,
            OverlayTexture.NO_OVERLAY
        ));
    }
}
