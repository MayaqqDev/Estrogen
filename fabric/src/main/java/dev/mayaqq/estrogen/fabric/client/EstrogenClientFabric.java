package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.client.EstrogenClient;
import net.fabricmc.api.ClientModInitializer;

public class EstrogenClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EstrogenClient.init();
    }
}
