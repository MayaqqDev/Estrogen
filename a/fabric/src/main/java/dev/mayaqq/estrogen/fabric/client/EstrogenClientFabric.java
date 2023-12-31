package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.client.EstrogenClient;
import dev.mayaqq.estrogen.networking.EstrogenS2C;
import dev.mayaqq.estrogen.registry.EstrogenClientEvents;
import dev.mayaqq.estrogen.registry.EstrogenFluidRender;
import dev.mayaqq.estrogen.registry.EstrogenModelPredicateProviders;
import dev.mayaqq.estrogen.registry.EstrogenResourcePacks;
import net.fabricmc.api.ClientModInitializer;

public class EstrogenClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EstrogenClient.init();
        EstrogenModelPredicateProviders.register();
        EstrogenClientEvents.register();
        EstrogenS2C.register();
        EstrogenFluidRender.register();
        EstrogenResourcePacks.register();
    }
}
