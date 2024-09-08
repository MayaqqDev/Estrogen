package dev.mayaqq.estrogen.fabric.client.models;

import dev.mayaqq.estrogen.utils.LocationResolver;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static dev.mayaqq.estrogen.utils.client.EstrogenClientPaths.*;

public class EstrogenModelLoadingPlugin implements PreparableModelLoadingPlugin<EstrogenModelLoadingPlugin.Preparations> {

    public static final EstrogenModelLoadingPlugin INSTANCE = new EstrogenModelLoadingPlugin();

    public static CompletableFuture<Preparations> prepare(ResourceManager manager, Executor executor) {
        CompletableFuture<LocationResolver> futureThighHighModels = CompletableFuture.supplyAsync(() ->
            LocationResolver.load(manager, THIGH_HIGH_MODELS_DIRECTORY, "models", ".json"),
            executor);

        CompletableFuture<LocationResolver> futureThighHighItems = CompletableFuture.supplyAsync(() ->
            LocationResolver.load(manager, THIGH_HIGH_ITEM_TEXTURES, "textures", ".png"),
            executor);

        return futureThighHighModels.thenCombineAsync(futureThighHighItems, Preparations::new, executor);
    }

    @Override
    public void onInitializeModelLoader(Preparations data, ModelLoadingPlugin.Context pluginContext) {
        pluginContext.addModels(data.thighHighModels.locations());
        pluginContext.modifyModelOnLoad().register((model, context) ->
            (context.id().equals(THIGH_HIGH_ITEM_LOCATION))
            ? new FabricThighHighItemModel(model, data.thighHighTextures)
            : model
        );
    }

    public record Preparations(LocationResolver thighHighModels, LocationResolver thighHighTextures) {}
}
