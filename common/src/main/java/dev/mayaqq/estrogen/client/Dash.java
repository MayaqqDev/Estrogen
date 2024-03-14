package dev.mayaqq.estrogen.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.Physics;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.UUID;

public class Dash {

    // the overlay texture
    private static final ResourceLocation DASH_OVERLAY = new ResourceLocation("textures/misc/nausea.png");
    // should the player be uwufied
    public static boolean uwufy = false;
    // is the dash on cooldown
    public static boolean onCooldown = false;
    // tick counter from 0 to 20
    private static int tick = 0;
    public static HashMap<UUID, Physics> physicsMap = new HashMap<>();

    public static void dashClientTick() {
        Minecraft client = Minecraft.getInstance();

        if (client.level != null && EstrogenConfig.client().chestPhysics.get()) {
            for (HashMap.Entry<UUID, Physics> physics : physicsMap.entrySet()) {
                Player player = client.level.getPlayerByUUID(physics.getKey());
                if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get())) {
                    physics.getValue().update(player);
                    if (physics.getValue().expired) {
                        physicsMap.remove(physics.getKey());
                    }
                } else {
                    physicsMap.remove(physics.getKey());
                }
            }
        }

        LocalPlayer player = client.player;
        if (player == null) return;

        // UwU Check
        tick++;
        if (tick == 20) {
            tick = 0;
            uwufy = player.getInventory().contains(EstrogenTags.Items.UWUFYING);
        }
    }

    public static void renderOverlayTick(GuiGraphics guiGraphics) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get()) && onCooldown && EstrogenConfig.client().dashOverlay.get()) {
            renderOverLayer(guiGraphics, 0.3F, 0.5F, 0.8F);
        }
    }

    private static void renderOverLayer(GuiGraphics graphics, float c1, float c2, float c3) {
        int i = graphics.guiWidth();
        int j = graphics.guiHeight();
        graphics.pose().pushPose();
        float distortionAmount = 0.5F;
        float f = Mth.lerp(distortionAmount, 2.0F, 1.0F);
        graphics.pose().translate((float) i / 2.0F, (float) j / 2.0F, 0.0F);
        graphics.pose().scale(f, f, f);
        graphics.pose().translate((float) (-i) / 2.0F, (float) (-j) / 2.0F, 0.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        graphics.setColor(c1, c2, c3, 1.0F);
        graphics.blit(DASH_OVERLAY, 0, 0, -90, 0.0F, 0.0F, i, j, i, j);
        RenderSystem.setShaderColor(c1, c2, c3, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        graphics.setColor(1f, 1f, 1f, 1f);
        graphics.pose().popPose();
    }
}
