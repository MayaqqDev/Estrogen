package dev.mayaqq.estrogen;

import dev.mayaqq.estrogen.networking.C2S;
import dev.mayaqq.estrogen.registry.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Estrogen implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Estrogen");
    public static Identifier id(String path) {
        return new Identifier("estrogen", path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Injecting Estrogen into your veins...");
        EffectRegistry.register();
        C2S.register();
        ItemRegistry.register();
        FoodCompontentRegistry.register();
        SoundRegistry.register();
        FluidRegistry.register();
        BlockRegistry.register();
        EntityRegistry.register();
    }
}