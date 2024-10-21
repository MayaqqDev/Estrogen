package dev.mayaqq.estrogen.client.cosmetics;

import com.google.gson.JsonObject;


import com.mojang.blaze3d.vertex.PoseStack;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.AnimationDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.Optional;
import java.util.function.Function;

public record Cosmetic(String id, String name, CosmeticTexture texture, CosmeticModel model, Optional<CosmeticAnimation> animation) {

    public static Cosmetic fromJson(String id, JsonObject json) {
        String name = GsonHelper.getAsString(json, "name", id);
        CosmeticTexture texture = new CosmeticTexture(json.get("texture").getAsString());
        CosmeticModel model = new CosmeticModel(json.get("model").getAsString());

        Optional<CosmeticAnimation> animation = json.has("animation")
            ? Optional.of(new CosmeticAnimation(json.get("animation").getAsString()))
            : Optional.empty();

        return new Cosmetic(id, name, texture, model, animation);
    }

    /**
     * Use this for rendering cosmetics
     * @param renderType Render type function, provides a RenderType for the texture, e.g. RenderType::entityCutout
     * @param source MultiBufferSource to render this cosmetic into
     * @param matrices PoseStack with transformations
     * @param light lighting
     */
    public void render(Function<ResourceLocation, RenderType> renderType, MultiBufferSource source, PoseStack matrices, int light, int overlay) {
        model.get().ifPresent(model -> {
            animation.flatMap(CosmeticAnimation::getResult)
                .ifPresent(model::runAnimation);

            model.getMesh().renderInto(
                source.getBuffer(renderType.apply(texture.getResourceLocation())),
                matrices,
                0xFFFFFFFF,
                light,
                overlay
            );
        });
    }

    public boolean useDefaultAnimation() {
        return animation.flatMap(CosmeticAnimation::getResult)
            .map(AnimationDefinition::defaultAnimation)
            .orElse(true);
    }

}
