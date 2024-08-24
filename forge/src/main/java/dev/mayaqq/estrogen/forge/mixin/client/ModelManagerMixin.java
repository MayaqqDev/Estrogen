package dev.mayaqq.estrogen.forge.mixin.client;

import dev.mayaqq.estrogen.forge.client.models.ForgeThighHighItemModel;
import dev.mayaqq.estrogen.utils.LocationResolver;
import dev.mayaqq.estrogen.utils.client.EstrogenCommonPaths;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ModelManager.class)
public class ModelManagerMixin {

    @Inject(
        method = "reload",
        at = @At("HEAD")
    )
    public void loadLocationResolver(PreparableReloadListener.PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        ForgeThighHighItemModel.forgeLocationResolver.set(
            LocationResolver.load(resourceManager, EstrogenCommonPaths.THIGH_HIGH_ITEM_TEXTURES, "textures", ".png")
        );
    }
}
