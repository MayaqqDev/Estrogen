package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class CookieJarRenderer implements BlockEntityRenderer<CookieJarBlockEntity> {

    public CookieJarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CookieJarBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        ItemStack cookie = be.getItem(0);
        int cookieCount = be.getCookieCount();

        poseStack.pushPose();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if ((Objects.requireNonNull(be.getLevel()).getBlockState(be.getBlockPos())).is(EstrogenBlocks.COOKIE_JAR.get())) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.translate(0.5, 0.35, -0.032F);
            int level = cookieCount == 0 ? 0 : Math.floorDiv(cookieCount, 128) + 1;
            for (int i = 0; i < level; ++i) {
                poseStack.translate(0, 0, -0.032F);
                itemRenderer.renderStatic(
                        cookie, ItemDisplayContext.GROUND,
                        light, overlay, poseStack, bufferSource,
                        be.getLevel(), 0
                );
                poseStack.translate(0.05, 0, -0.032F);
                itemRenderer.renderStatic(
                        cookie, ItemDisplayContext.GROUND,
                        light, overlay, poseStack, bufferSource,
                        be.getLevel(), 0
                );
                poseStack.translate(-0.05, 0.05, -0.032F);
                itemRenderer.renderStatic(
                        cookie, ItemDisplayContext.GROUND,
                        light, overlay, poseStack, bufferSource,
                        be.getLevel(), 0
                );
                poseStack.translate(0, -0.05, 0);
            }
        }
        poseStack.popPose();
    }
}