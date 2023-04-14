package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.networking.S2C;
import net.fabricmc.api.ClientModInitializer;

public class EstrogenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeybindRegistry.register();
        ClientEventRegistry.register();
        S2C.register();
        FluidRenderRegistry.register();
        PackRegistry.register();
    }
}
