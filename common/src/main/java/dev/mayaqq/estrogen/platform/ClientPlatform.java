package dev.mayaqq.estrogen.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.material.Fluid;

public class ClientPlatform {
    @ExpectPlatform
    public static void fluidRenderLayerMap(Fluid fluid, RenderType type) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void applyItemTransforms(BakedModel model, ItemDisplayContext context, PoseStack poseStack) {
        throw new AssertionError();
    }
}
