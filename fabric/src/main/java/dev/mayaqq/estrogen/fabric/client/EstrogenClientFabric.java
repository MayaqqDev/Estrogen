package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.client.EstrogenClient;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.RenderType;

import static dev.mayaqq.estrogen.Estrogen.LOGGER;

public class EstrogenClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // init Estrogen Client on fabric
        EstrogenClient.init();
        EstrogenFabricClientEvents.register();
        EstrogenFluids.FLUIDS.stream().forEach(fluid -> BlockRenderLayerMap.INSTANCE.putFluid(fluid.get(), RenderType.translucent()));
    }
}
