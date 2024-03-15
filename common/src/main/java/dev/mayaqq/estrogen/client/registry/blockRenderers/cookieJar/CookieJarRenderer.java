package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;

public class CookieJarRenderer implements BlockEntityRenderer<CookieJarBlockEntity> {

    public CookieJarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CookieJarBlockEntity be, float partialTick, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
        ms.pushPose();
        ms.translate(0.5,0.5,0.5);
        ms.scale(0.8F,0.8F,0.8F);
        mc.getItemRenderer().renderStatic(be.getItem(0), ItemDisplayContext.GROUND, light, overlay, ms, bufferSource, be.getLevel(), 0);
        ms.popPose();
    }
}
