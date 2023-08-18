package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.networking.EstrogenS2C;
import dev.mayaqq.estrogen.registry.client.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class EstrogenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EstrogenKeybinds.register();
        Dash.register();
        EstrogenS2C.register();
        EstrogenFluidRender.register();
        EstrogenResourcePacks.register();
        EstrogenRenderer.register();
        EstrogenModelPredicateProviders.register();

        if (FabricLoader.getInstance().isModLoaded("ears")) {
            EarsCompat.boob();
        }

    }
}
