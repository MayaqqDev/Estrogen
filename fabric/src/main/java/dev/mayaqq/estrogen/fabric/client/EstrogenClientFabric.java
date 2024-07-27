package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClient;
import dev.mayaqq.estrogen.resources.BreastArmorDataLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class EstrogenClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // init Estrogen Client on fabric
        EstrogenClient.init();
        EstrogenFabricClientEvents.register();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new Listener());
    }

    private static class Listener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return Estrogen.id("estrogen_armor_data");
        }

        @Override
        public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
            return BreastArmorDataLoader.INSTANCE.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
        }
    }
}
