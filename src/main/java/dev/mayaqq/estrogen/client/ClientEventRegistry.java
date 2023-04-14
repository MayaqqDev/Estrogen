package dev.mayaqq.estrogen.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import static dev.mayaqq.estrogen.client.KeybindRegistry.dashKey;

public class ClientEventRegistry {
    private static final Identifier DASH_OVERLAY = new Identifier("textures/misc/nausea.png");
    public static int dashCooldown = 0;
    public static short maxDashes = 0;
    public static short currentDashes = 0;
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            dashCooldown--;
            if (dashCooldown > 0) return;
            if (dashCooldown < 0) dashCooldown = 0;
            ClientPlayerEntity player = client.player;
            if (player == null) return;
            if (player.isOnGround()) {
                currentDashes = maxDashes;
            }
            if (dashKey.wasPressed() && currentDashes > 0) {
                dashCooldown = 10;
                currentDashes--;
                player.setVelocity(player.getRotationVector().x * 2, player.getRotationVector().y * 2, player.getRotationVector().z * 2);
                ClientPlayNetworking.send(Estrogen.id("dash"), PacketByteBufs.empty());
            }
        });
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            if (dashCooldown > 0) {
                MinecraftClient mc = MinecraftClient.getInstance();
                float distortionStrength = 0.5F;
                int i = mc.getWindow().getScaledWidth();
                int j = mc.getWindow().getScaledHeight();
                double d = MathHelper.lerp(distortionStrength, 2.0, 1.0);
                float f = 0.3F; // red component
                float g = 0.5F; // green component
                float h = 0.8F; // blue component
                double e = (double)i * d;
                double k = (double)j * d;
                double l = ((double)i - e) / 2.0;
                double m = ((double)j - k) / 2.0;
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE);
                RenderSystem.setShaderColor(f, g, h, 1.0F);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, DASH_OVERLAY);
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();
                bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                bufferBuilder.vertex(l, m + k, -90.0).texture(0.0F, 1.0F).next();
                bufferBuilder.vertex(l + e, m + k, -90.0).texture(1.0F, 1.0F).next();
                bufferBuilder.vertex(l + e, m, -90.0).texture(1.0F, 0.0F).next();
                bufferBuilder.vertex(l, m, -90.0).texture(0.0F, 0.0F).next();
                tessellator.draw();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.defaultBlendFunc();
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
            }
        });

    }
}