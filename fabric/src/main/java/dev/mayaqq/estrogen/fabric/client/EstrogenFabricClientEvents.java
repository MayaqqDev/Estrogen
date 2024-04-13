package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.client.config.ConfigSync;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class EstrogenFabricClientEvents {
    public static void register() {
        HudRenderCallback.EVENT.register((guiGraphics, delta) -> {
            Dash.renderOverlayTick(guiGraphics);
        });
        ModConfigEvents.loading(Estrogen.MOD_ID).register(ConfigSync::onLoad);
        ModConfigEvents.reloading(Estrogen.MOD_ID).register(ConfigSync::onReload);
    }
}
