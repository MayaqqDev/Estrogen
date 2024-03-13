package dev.mayaqq.estrogen.client;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.mayaqq.estrogen.integrations.ears.EarsCompat;
import dev.mayaqq.estrogen.networking.EstrogenS2C;
import dev.mayaqq.estrogen.registry.client.EstrogenKeybinds;
import dev.mayaqq.estrogen.registry.client.EstrogenModelPredicateProviders;
import dev.mayaqq.estrogen.registry.client.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenPonderScenes;
import net.minecraft.client.renderer.RenderType;

import static dev.mayaqq.estrogen.Estrogen.LOGGER;

public class EstrogenClient {
    public static void init() {
        Dash.register();
        EstrogenRenderer.register();
        EstrogenKeybinds.register();
        EstrogenModelPredicateProviders.register();
        EstrogenS2C.register();
        EstrogenPonderScenes.register();

        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.LIQUID_ESTROGEN.get());
        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.LIQUID_ESTROGEN_FLOWING.get());
        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.HORSE_URINE.get());
        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.HORSE_URINE_FLOWING.get());
        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.FILTRATED_HORSE_URINE.get());
        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.FILTRATED_HORSE_URINE_FLOWING.get());
        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.TESTOSTERONE_MIXTURE.get());
        RenderTypeRegistry.register(RenderType.translucent(), EstrogenFluids.TESTOSTERONE_MIXTURE_FLOWING.get());

        // mod compat
        if (Platform.isModLoaded("ears")) {
            EarsCompat.boob();
        }
    }
}
