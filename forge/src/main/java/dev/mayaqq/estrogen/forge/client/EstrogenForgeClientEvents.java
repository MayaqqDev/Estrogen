package dev.mayaqq.estrogen.forge.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.Dash;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID, value = Dist.CLIENT)
public class EstrogenForgeClientEvents {
    @SubscribeEvent
    public static void onGuiRenderEvent(RenderGuiEvent event) {
        Dash.renderOverlayTick(event.getGuiGraphics());
    }
}