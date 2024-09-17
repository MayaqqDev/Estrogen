package dev.mayaqq.estrogen.fabric;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.fabric.biome.EstrogenBiomeModifications;
import dev.mayaqq.estrogen.fabric.loot.ThighHighStyleLootFunction;
import dev.mayaqq.estrogen.resources.thighhighs.ThighHighStyleLoader;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class EstrogenFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Register and configure the config
        EstrogenConfig.register((type, spec) ->
            ForgeConfigRegistry.INSTANCE.register(Estrogen.MOD_ID, type, spec)
        );

        // init Estrogen main class
        Estrogen.init();

        // Register custom loot function
        ThighHighStyleLootFunction.register();

        // Register Biome Modifications
        EstrogenBiomeModifications.addBiomeModifications();

        // Register server reload listener
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new Listener());

        // Register Fabric specific Events
        EstrogenFabricEvents.register();

        ModConfigEvents.loading(Estrogen.MOD_ID).register(EstrogenConfig::onLoad);
        ModConfigEvents.reloading(Estrogen.MOD_ID).register(EstrogenConfig::onReload);
    }

    private static class Listener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return Estrogen.id("thigh_high_styles");
        }

        @Override
        public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
            return ThighHighStyleLoader.INSTANCE.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
        }
    }
}