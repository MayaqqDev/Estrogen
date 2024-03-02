package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.client.Dash;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class EstrogenFabricClientEvents {
    public static void register() {
        HudRenderCallback.EVENT.register((guiGraphics, delta) -> {
            Dash.renderOverlayTick(guiGraphics);
        });
    }
}
