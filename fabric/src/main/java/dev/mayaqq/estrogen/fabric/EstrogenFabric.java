package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.api.ModInitializer;

public class EstrogenFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Estrogen.init();
    }
}