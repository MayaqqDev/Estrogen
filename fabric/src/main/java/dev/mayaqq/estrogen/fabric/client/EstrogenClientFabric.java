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

        // Fabric please add conditional dependencies
        if (FabricLoader.getInstance().isModLoaded("roughlyenoughitems") && !FabricLoader.getInstance().isModLoaded("createreibugfix")) {
            LOGGER.warn("--------------------------------------------------------------------------------------------------------------------------");
            LOGGER.warn("");
            LOGGER.warn("[ESTROGEN] Roughly Enough Items is installed without Create REI Bugfix! This will cause issues with some Estrogen Recipes.");
            LOGGER.warn("[ESTROGEN] Please install Create REI Bugfix to fix this issue: https://modrinth.com/mod/createfabricreibugfix");
            LOGGER.warn("");
            LOGGER.warn("--------------------------------------------------------------------------------------------------------------------------");
        }
    }
}
