package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class CookieJarRenderer implements BlockEntityRenderer<CookieJarBlockEntity> {

    public CookieJarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CookieJarBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        if (be.getLevel() == null) {
            return;
        }

        // TODO: all this lol

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        poseStack.translate(0.5, 0.35, -0.032F);

        for (int i = 0; i < 8; i++) {
            ItemStack jarItem = be.getItem(i);

            if (jarItem.isEmpty()) {
                continue;
            }
            poseStack.translate(0.025, 0.025, -0.032F);
            itemRenderer.renderStatic(
                    jarItem, ItemDisplayContext.GROUND,
                    light, overlay, poseStack, bufferSource,
                    be.getLevel(), 0
            );
            poseStack.translate(-0.05, -0.05, -0.032F);
            itemRenderer.renderStatic(
                    jarItem, ItemDisplayContext.GROUND,
                    light, overlay, poseStack, bufferSource,
                    be.getLevel(), 0
            );
            poseStack.translate(0.025, 0.025, 0);
        }

        poseStack.popPose();
    }
}