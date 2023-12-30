package dev.mayaqq.estrogen.client;

import dev.architectury.platform.Platform;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.networking.EstrogenS2C;
import dev.mayaqq.estrogen.registry.client.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import static dev.mayaqq.estrogen.Estrogen.LOGGER;

public class EstrogenClient {
    public static void init() {
        EstrogenKeybinds.register();
        Dash.register();
        EstrogenS2C.register();
        EstrogenFluidRender.register();
        EstrogenResourcePacks.register();
        EstrogenRenderer.register();
        EstrogenModelPredicateProviders.register();
        EstrogenClientEvents.register();

        // mod compat
        if (Platform.isModLoaded("ears")) {
            EarsCompat.boob();
        }

        if (Platform.isFabric() && Platform.isModLoaded("roughlyenoughitems") && !Platform.isModLoaded("createreibugfix")) {
            LOGGER.warn("--------------------------------------------------------------------------------------------------------------------------");
            LOGGER.warn("");
            LOGGER.warn("[ESTROGEN] Roughly Enough Items is installed without Create REI Bugfix! This will cause issues with some Estrogen Recipes.");
            LOGGER.warn("[ESTROGEN] Please install Create REI Bugfix to fix this issue: https://modrinth.com/mod/createfabricreibugfix");
            LOGGER.warn("");
            LOGGER.warn("--------------------------------------------------------------------------------------------------------------------------");
        }
    }
}
