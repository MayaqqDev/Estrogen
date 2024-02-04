package dev.mayaqq.estrogen.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;

public class Dash {

    // should the player be uwufied
    public static boolean uwufy = false;
    // tick counter from 0 to 20
    private static int tick = 0;
    // the overlay texture
    private static final ResourceLocation DASH_OVERLAY = new ResourceLocation("textures/misc/nausea.png");
    public static final TagKey<Item> UWUFYING = TagKey.create(Registry.ITEM.key(), new ResourceLocation("estrogen", "uwufying"));
    // is the dash on cooldown
    public static boolean onCooldown = false;

    public static void register() {
        ClientTickEvent.CLIENT_POST.register(client -> {
            LocalPlayer player = client.player;
            if (player == null) return;

            // UwU Check
            tick++;
            if (tick == 20) {
                tick = 0;
                uwufy = player.getInventory().contains(UWUFYING);
            }
        });
        ClientGuiEvent.RENDER_HUD.register((graphics, tickDelta) -> {
            if (onCooldown) {
                renderOverLayer(0.3F, 0.5F, 0.8F);
            }
        });
    }

    private static void renderOverLayer(float f, float g, float h) {
        Minecraft mc = Minecraft.getInstance();
        float distortionStrength = 0.5F;
        int i = mc.getWindow().getGuiScaledWidth();
        int j = mc.getWindow().getGuiScaledHeight();
        double d = Mth.lerp(distortionStrength, 2.0, 1.0);
        double e = (double)i * d;
        double k = (double)j * d;
        double l = ((double)i - e) / 2.0;
        double m = ((double)j - k) / 2.0;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        RenderSystem.setShaderColor(f, g, h, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, DASH_OVERLAY);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(l, m + k, -90.0).uv(0.0F, 1.0F).endVertex();
        bufferBuilder.vertex(l + e, m + k, -90.0).uv(1.0F, 1.0F).endVertex();
        bufferBuilder.vertex(l + e, m, -90.0).uv(1.0F, 0.0F).endVertex();
        bufferBuilder.vertex(l, m, -90.0).uv(0.0F, 0.0F).endVertex();
        tessellator.end();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
