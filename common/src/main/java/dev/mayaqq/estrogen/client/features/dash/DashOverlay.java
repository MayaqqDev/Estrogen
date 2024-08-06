package dev.mayaqq.estrogen.client.features.dash;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

public class DashOverlay {

    private static final ResourceLocation DASH_OVERLAY = new ResourceLocation("textures/misc/nausea.png");

    public static void drawOverlay(GuiGraphics guiGraphics) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get()) && ClientDash.isOnCooldown() && EstrogenConfig.client().dashOverlay.get()) {
            renderOverlay(guiGraphics, 0.3F, 0.5F, 0.8F);
        }
        if (DreamBlockEffect.isInDreamBlock()) {
            renderOverlay(guiGraphics, 0.2F, 0.0F, 0.2F);
        }
    }

    private static void renderOverlay(GuiGraphics graphics, float red, float green, float blue) {
        try (var stack = new CloseablePoseStack(graphics)) {
            stack.translate((float) graphics.guiWidth() / 2.0F, (float) graphics.guiHeight() / 2.0F, 0.0F);
            stack.scale(1.5f, 1.5f, 1.5f);
            stack.translate((float) (-graphics.guiWidth()) / 2.0F, (float) (-graphics.guiHeight()) / 2.0F, 0.0F);

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            graphics.setColor(red, green, blue, 1.0F);
            graphics.blit(DASH_OVERLAY, 0, 0, -90, 0.0F, 0.0F, graphics.guiWidth(), graphics.guiHeight(), graphics.guiWidth(), graphics.guiHeight());
            RenderSystem.setShaderColor(red, green, blue, 1.0F);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            graphics.setColor(1f, 1f, 1f, 1f);
        }
    }
}
