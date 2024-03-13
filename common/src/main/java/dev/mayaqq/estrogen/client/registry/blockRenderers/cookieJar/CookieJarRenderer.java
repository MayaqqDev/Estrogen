package dev.mayaqq.estrogen.client.registry.blockRenderers.cookieJar;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;

public class CookieJarRenderer extends SafeBlockEntityRenderer<CookieJarBlockEntity> {

    public CookieJarRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    protected void renderSafe(CookieJarBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer().renderStatic(be.getTheItem(), ItemDisplayContext.FIXED, light, overlay, ms, bufferSource, mc.level, 0);
    }
}
