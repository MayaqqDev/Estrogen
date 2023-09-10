package dev.mayaqq.estrogen.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.datagen.tags.EstrogenTags;
import dev.mayaqq.estrogen.networking.EstrogenC2S;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import static dev.mayaqq.estrogen.registry.client.EstrogenKeybinds.dashKey;

public class Dash {

    public static boolean uwufy = false;
    public static boolean uwufyHotbar = false;
    private static int tick = 0;

    private static final Identifier DASH_OVERLAY = new Identifier("textures/misc/nausea.png");
    public static int dashCooldown = 0;
    public static int groundCooldown = 0;
    public static boolean onCooldown = false;
    public static short maxDashes = 0;
    public static short currentDashes = 0;
    private static boolean shouldWaveDash = false;
    private static BlockPos lastPos = null;
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;
            if (player == null) return;

            // UwU
            tick++;
            if (tick == 20) {
                tick = 0;
                uwufy = player.getInventory().contains(EstrogenTags.ItemTags.UWUFYING);
                boolean turnoff = true;
                for (int i = 0; i < 9; i++) {
                    if (player.getInventory().getStack(i).getItem().getDefaultStack().isIn(EstrogenTags.ItemTags.UWUFYING)) {
                        uwufyHotbar = true;
                        turnoff = false;
                        break;
                    }
                }
                if (turnoff) uwufyHotbar = false;
            }

            // Dash
            dashCooldown--;
            groundCooldown--;
            if (dashCooldown < 0) dashCooldown = 0;
            if (groundCooldown < 0) groundCooldown = 0;
            if (!player.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                maxDashes = 0;
                currentDashes = 0;
                onCooldown = false;
                dashCooldown = 0;
                return;
            }

            if (dashCooldown > 0 && dashCooldown % 2 == 0 && player.getBlockPos() != lastPos) {
                ClientPlayNetworking.send(EstrogenC2S.DASH_PARTICLES, PacketByteBufs.empty());
            }
            lastPos = player.getBlockPos();

            if (dashCooldown > 0 && shouldWaveDash && client.options.jumpKey.isPressed()) {
                player.setVelocity(player.getRotationVector().x * 3, 1, player.getRotationVector().z * 3);
                shouldWaveDash = false;
            }

            if (shouldRefreshDash(player) && groundCooldown == 0) {
                groundCooldown = 4;
                currentDashes = maxDashes;
            }
            onCooldown = dashCooldown > 0 || currentDashes == 0;
            if (dashKey.wasPressed() && player.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT) && !onCooldown) {
                if (player.getPitch() > 50 && player.getPitch() < 90) {
                    shouldWaveDash = true;
                }
                dashCooldown = 10;
                currentDashes--;
                player.setVelocity(player.getRotationVector().x * 2, player.getRotationVector().y * 2, player.getRotationVector().z * 2);
                ClientPlayNetworking.send(Estrogen.id("dash"), PacketByteBufs.empty());
            }
        });

        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            if (onCooldown) {
                renderOverLayer(graphics, 0.3F, 0.5F, 0.8F);
            }
            if (uwufyHotbar) {
                renderOverLayer(graphics, 0.9F, 0.6F, 0.7F);
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
        graphics.getMatrices().pop();
    }

    private static Boolean shouldRefreshDash(ClientPlayerEntity player) {
        return player.isOnGround() || player.getWorld().getBlockState(player.getBlockPos()).getBlock() instanceof FluidBlock;
    }
}