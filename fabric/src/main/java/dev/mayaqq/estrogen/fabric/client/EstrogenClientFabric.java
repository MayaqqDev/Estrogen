package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClient;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.EstrogenShaders;
import dev.mayaqq.estrogen.fabric.client.models.EstrogenModelLoadingPlugin;
import dev.mayaqq.estrogen.resources.BreastArmorDataLoader;
import io.github.fabricators_of_create.porting_lib.event.client.EntityAddedLayerCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
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
        CoreShaderRegistrationCallback.EVENT.register(context -> EstrogenShaders.register(context::register));
        EntityAddedLayerCallback.EVENT.register((renderers, skinMap) -> EstrogenRenderer.registerEntityLayers(skinMap::get));

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new Listener());

        // This is needed to automatically load all models in models/thigh_high_styles
        // Pretty fabric way using model loading plugin api
        PreparableModelLoadingPlugin.register(EstrogenModelLoadingPlugin::prepare, EstrogenModelLoadingPlugin.INSTANCE);
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
