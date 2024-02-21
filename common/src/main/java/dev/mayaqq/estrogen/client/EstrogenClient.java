package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.client.registry.EstrogenModelPredicateProviders;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.trinkets.EstrogenPatchesRenderer;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenPonderScenes;
import earth.terrarium.botarium.client.ClientHooks;
import earth.terrarium.botarium.util.CommonHooks;
import net.minecraft.client.renderer.RenderType;

import static dev.mayaqq.estrogen.Estrogen.LOGGER;

public class EstrogenClient {
    public static void init() {
        Dash.register();
        EstrogenRenderer.register();
        EstrogenKeybinds.register();
        EstrogenModelPredicateProviders.register();
        EstrogenPonderScenes.register();
        EstrogenPatchesRenderer.register();

        //TODO: Might not work
        ClientHooks.setRenderLayer(EstrogenBlocks.LIQUID_ESTROGEN_BLOCK.get(), RenderType.translucent());
        ClientHooks.setRenderLayer(EstrogenBlocks.HORSE_URINE_BLOCK.get(), RenderType.translucent());
        ClientHooks.setRenderLayer(EstrogenBlocks.FILTRATED_HORSE_URINE_BLOCK.get(), RenderType.translucent());
        ClientHooks.setRenderLayer(EstrogenBlocks.TESTOSTERONE_MIXTURE_BLOCK.get(), RenderType.translucent());

        // mod compat
        if (CommonHooks.isModLoaded("ears")) {
            EarsCompat.boob();
        }

        if (CommonHooks.isModLoaded("roughlyenoughitems") && !CommonHooks.isModLoaded("createreibugfix")) {
            LOGGER.warn("--------------------------------------------------------------------------------------------------------------------------");
            LOGGER.warn("");
            LOGGER.warn("[ESTROGEN] Roughly Enough Items is installed without Create REI Bugfix! This will cause issues with some Estrogen Recipes.");
            LOGGER.warn("[ESTROGEN] Please install Create REI Bugfix to fix this issue: https://modrinth.com/mod/createfabricreibugfix");
            LOGGER.warn("");
            LOGGER.warn("--------------------------------------------------------------------------------------------------------------------------");
        }
    }
}
