package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.trinkets.EstrogenPatchesRenderer;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenPonderScenes;
import earth.terrarium.botarium.client.ClientHooks;
import earth.terrarium.botarium.util.CommonHooks;
import net.minecraft.client.renderer.RenderType;

public class EstrogenClient {
    public static void init() {
        EstrogenRenderer.register();
        EstrogenPonderScenes.register();
        EstrogenKeybinds.register();
        EstrogenPatchesRenderer.register();
        ClientHooks.setRenderLayer(EstrogenBlocks.CENTRIFUGE.get(), RenderType.cutout());

        // mod compat
        if (CommonHooks.isModLoaded("ears")) {
            EarsCompat.boob();
        }
    }
}
