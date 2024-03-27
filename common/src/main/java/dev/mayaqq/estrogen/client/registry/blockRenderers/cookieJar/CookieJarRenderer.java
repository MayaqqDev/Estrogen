package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import dev.mayaqq.estrogen.registry.blocks.CookieJarBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class CookieJarRenderer implements BlockEntityRenderer<CookieJarBlockEntity> {
    private static final ItemStack COOKIE = new ItemStack(Items.COOKIE);

    public CookieJarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CookieJarBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        BlockState state;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if ((state = Objects.requireNonNull(be.getLevel()).getBlockState(be.getBlockPos())).is(EstrogenBlocks.COOKIE_JAR.get())) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.translate(0.5, 0.35, -0.032F);
            for (int i = 0; i < state.getValue(CookieJarBlock.COOKIE_LEVEL); ++i) {
                poseStack.translate(0, 0, -0.032F);
                itemRenderer.renderStatic(
                        COOKIE, ItemDisplayContext.GROUND,
                        light, overlay, poseStack, bufferSource,
                        be.getLevel(), 0
                );
                poseStack.translate(0.05, 0, -0.032F);
                itemRenderer.renderStatic(
                        COOKIE, ItemDisplayContext.GROUND,
                        light, overlay, poseStack, bufferSource,
                        be.getLevel(), 0
                );
                poseStack.translate(-0.05, 0.05, -0.032F);
                itemRenderer.renderStatic(
                        COOKIE, ItemDisplayContext.GROUND,
                        light, overlay, poseStack, bufferSource,
                        be.getLevel(), 0
                );
                poseStack.translate(0, -0.05, 0);
            }
        }
        poseStack.popPose();
    }
}