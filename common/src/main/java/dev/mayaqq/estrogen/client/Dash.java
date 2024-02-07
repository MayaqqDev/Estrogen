package dev.mayaqq.estrogen.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;

public class Dash {

    // the overlay texture
    private static final ResourceLocation DASH_OVERLAY = new ResourceLocation("textures/misc/nausea.png");
    // the uwufying tag
    public static final TagKey<Item> UWUFYING = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("estrogen", "uwufying"));
    // should the player be uwufied
    public static boolean uwufy = false;
    // is the dash on cooldown
    public static boolean onCooldown = false;
    // tick counter from 0 to 20
    private static int tick = 0;

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
            if (onCooldown && EstrogenConfig.client().dashOverlay.get()) {
                renderOverLayer(graphics, 0.3F, 0.5F, 0.8F);
            }
        });
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
