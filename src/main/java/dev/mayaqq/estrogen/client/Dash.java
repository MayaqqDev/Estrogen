package dev.mayaqq.estrogen.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class Dash {

    // should the player be uwufied
    public static boolean uwufy = false;
    // tick counter from 0 to 20
    private static int tick = 0;
    // the overlay texture
    private static final Identifier DASH_OVERLAY = new Identifier("textures/misc/nausea.png");
    // is the dash on cooldown
    public static boolean onCooldown = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player == null) return;

            // UwU Check
            tick++;
            if (tick == 20) {
                tick = 0;
                uwufy = player.getInventory().contains(EstrogenTags.ItemTags.UWUFYING);
            }
        });

        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            if (onCooldown) {
                renderOverLayer(graphics, 0.3F, 0.5F, 0.8F);
            }
        });
    }

    private static void renderOverLayer(GuiGraphics graphics, float c1, float c2, float c3) {
        int i = graphics.getScaledWindowWidth();
        int j = graphics.getScaledWindowHeight();
        graphics.getMatrices().push();
        float distortionAmount = 0.5F;
        float f = MathHelper.lerp(distortionAmount, 2.0F, 1.0F);
        graphics.getMatrices().translate((float)i / 2.0F, (float)j / 2.0F, 0.0F);
        graphics.getMatrices().scale(f, f, f);
        graphics.getMatrices().translate((float)(-i) / 2.0F, (float)(-j) / 2.0F, 0.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        graphics.setShaderColor(c1, c2, c3, 1.0F);
        graphics.drawTexture(DASH_OVERLAY, 0, 0, -90, 0.0F, 0.0F, i, j, i, j);
        RenderSystem.setShaderColor(c1, c2, c3, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        graphics.setShaderColor(1f, 1f, 1f, 1f);
        graphics.getMatrices().pop();
    }
}
