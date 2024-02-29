package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.trinkets.EstrogenPatchesRenderer;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.registry.EstrogenPonderScenes;
import earth.terrarium.botarium.util.CommonHooks;

public class EstrogenClient {
    public static void init() {
        EstrogenRenderer.register();
        EstrogenPonderScenes.register();
        EstrogenKeybinds.register();
        EstrogenPatchesRenderer.register();

        // mod compat
        if (CommonHooks.isModLoaded("ears")) {
            EarsCompat.boob();
        }
    }
}
