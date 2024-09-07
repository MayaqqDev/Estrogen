package dev.mayaqq.estrogen.platform.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.material.Fluid;

public class ClientPlatformImpl {
    public static void fluidRenderLayerMap(Fluid fluid, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(fluid, type);
    }

    public static void applyItemTransforms(BakedModel model, ItemDisplayContext context, PoseStack poseStack) {
        model.applyTransform(context, poseStack, false);
    }
}
