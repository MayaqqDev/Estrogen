package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.fabric.platformSpecific.EstrogenFabricEvents;
import net.fabricmc.api.ModInitializer;

public class EstrogenFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Estrogen.init();
        EstrogenFabricEvents.register();
    }
}