package dev.mayaqq.estrogen.platform.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.material.Fluid;

public class ClientPlatformImpl {
    public static void fluidRenderLayerMap(Fluid fluid, RenderType type) {
        BlockRenderLayerMap.INSTANCE.putFluid(fluid, type);
    }

    public static void applyItemTransforms(BakedModel model, ItemDisplayContext context, PoseStack poseStack) {
        model.getTransforms().getTransform(context).apply(false, poseStack);
    }
}
