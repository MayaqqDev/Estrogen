package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class CookieJarRenderer implements BlockEntityRenderer<CookieJarBlockEntity> {
    private final ItemRenderer itemRenderer;

    public CookieJarRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(CookieJarBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        if (be.getLevel() == null) {
            return;
        }

        poseStack.pushPose();
        poseStack.mulPose(Axis.XN.rotationDegrees(90));
        poseStack.translate(0.5, -0.625, 0.032F);

        for (ItemStack jarItem : be.getItems()) {
            if (jarItem.isEmpty()) {
                continue;
            }
            poseStack.translate(-0.025, -0.025, 0.032F);
            itemRenderer.renderStatic(
                    jarItem, ItemDisplayContext.GROUND,
                    light, overlay, poseStack, bufferSource,
                    be.getLevel(), 0
            );
            poseStack.translate(0.05, 0.05, 0.032F);
            itemRenderer.renderStatic(
                    jarItem, ItemDisplayContext.GROUND,
                    light, overlay, poseStack, bufferSource,
                    be.getLevel(), 0
            );
            poseStack.translate(-0.025, -0.025, 0);
        }

        poseStack.popPose();
    }
}