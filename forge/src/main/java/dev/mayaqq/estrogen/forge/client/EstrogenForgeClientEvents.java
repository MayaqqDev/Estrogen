package dev.mayaqq.estrogen.forge.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.Dash;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID, value = Dist.CLIENT)
public class EstrogenForgeClientEvents {
    @SubscribeEvent
    public static void onGuiRenderEvent(RenderGuiEvent event) {
        Dash.renderOverlayTick(event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
        Dash.uwufy = false;
        Minecraft.getInstance().updateTitle();
    }
}