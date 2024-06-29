package dev.mayaqq.estrogen.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.entity.player.features.boobs.Physics;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenSounds;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import dev.mayaqq.estrogen.registry.blocks.DreamBlock;
import dev.mayaqq.estrogen.registry.sounds.DreamBlockSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Dash {

    // the overlay texture
    private static final ResourceLocation DASH_OVERLAY = new ResourceLocation("textures/misc/nausea.png");
    // should the player be uwufied
    public static boolean uwufy = false;
    // is the dash on cooldown
    public static boolean onCooldown = false;
    // tick counter from 0 to 20
    private static int tick = 0;
    public static ConcurrentHashMap<UUID, Physics> physicsMap = new ConcurrentHashMap<>();

    // Dream Block
    private static boolean isInDreamBlock;
    private static int dreamBlockTick = 0;

    private static DreamBlockSoundInstance dreamBlockSoundInstance = null;

    public static void dashClientTick() {
        Minecraft client = Minecraft.getInstance();

        // client.player nullability check to stop IDE complaints (be safe out there!)
        if(client.player != null && client.level != null) {
            if (DreamBlock.isInDreamBlock(client.player)) {
                dreamBlockTick++;
                if (dreamBlockTick == 1) {
                    client.player.playSound(EstrogenSounds.DREAM_BLOCK_ENTER.get(), 1.0F, 1.0F);
                } else if (dreamBlockTick == 2) {
                    if (dreamBlockSoundInstance == null) {
                        dreamBlockSoundInstance = new DreamBlockSoundInstance(client.player);
                    }
                    client.getSoundManager().play(dreamBlockSoundInstance);
                }
                isInDreamBlock = true;
            } else {
                if (isInDreamBlock) {
                    client.player.playSound(EstrogenSounds.DREAM_BLOCK_EXIT.get(), 1.0F, 1.0F);

                }
                if (dreamBlockSoundInstance != null) {
                    client.getSoundManager().stop(dreamBlockSoundInstance);
                    dreamBlockSoundInstance = null;
                }
                dreamBlockTick = 0;
                isInDreamBlock = false;
            }

            if (client.level != null && EstrogenConfig.client().chestPhysicsRendering.get()) {
                for (ConcurrentHashMap.Entry<UUID, Physics> physics : physicsMap.entrySet()) {
                    Player player = client.level.getPlayerByUUID(physics.getKey());
                    if (player != null && player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get())) {
                        physics.getValue().update(player);
                        if (physics.getValue().expired) {
                            physicsMap.remove(physics.getKey());
                        }
                    } else {
                        physicsMap.remove(physics.getKey());
                    }
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
            client.updateTitle();
        }
    }


    public static void renderOverlayTick(GuiGraphics guiGraphics) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get()) && onCooldown && EstrogenConfig.client().dashOverlay.get()) {
            renderOverLayer(guiGraphics, 0.3F, 0.5F, 0.8F);
        }
        if (isInDreamBlock) {
            renderOverLayer(guiGraphics, 0.2F, 0.0F, 0.2F);
            // TODO: Texture
            //renderTextureOverlay(guiGraphics, DREAM_BLOCK_OVERLAY, 255);
        }
    }

    @SuppressWarnings("SameParameterValue")
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

    private static void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation shaderLocation, float alpha) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        guiGraphics.blit(shaderLocation, 0, 0, -90, 0.0F, 0.0F, guiGraphics.guiWidth(), guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight());
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
