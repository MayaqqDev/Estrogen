package dev.mayaqq.estrogen.forge.mixin.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.forge.client.models.ForgeThighHighItemModel;
import dev.mayaqq.estrogen.utils.client.EstrogenClientPaths;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> topLevelModels;

    @Shadow protected abstract BlockModel loadBlockModel(ResourceLocation location) throws IOException;

    @Shadow @Final public static FileToIdConverter MODEL_LISTER;

    @Shadow public abstract UnbakedModel getModel(ResourceLocation modelLocation);

    @Shadow @Final private Map<ResourceLocation, UnbakedModel> unbakedCache;

    // Ugly hardcoded forge mixin because forge's api sucks
    @Inject(
        method = "<init>",
        at = @At("RETURN")
    )
    public void loadAdditionalModels(BlockColors blockColors, ProfilerFiller profilerFiller, Map<ResourceLocation, BlockModel> modelResources, Map blockStateResources, CallbackInfo ci) {
        modelResources.keySet().forEach(id -> {
            if(id.getPath().startsWith(EstrogenClientPaths.THIGH_HIGH_MODELS_DIRECTORY)) {
                ResourceLocation id2 = MODEL_LISTER.fileToId(id);
                try {
                    UnbakedModel model = loadBlockModel(id2);
                    unbakedCache.put(id2, model);
                    topLevelModels.put(id2, model);
                    model.resolveParents(this::getModel);
                } catch (IOException e) {
                    Estrogen.LOGGER.error(e.getMessage());
                }
            }
        });
    }

    @Redirect(
        method = "loadTopLevel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/ModelBakery;getModel(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/resources/model/UnbakedModel;"
        )
    )
    public UnbakedModel wrapModel(ModelBakery instance, ResourceLocation modelLocation) {
        if(modelLocation.equals(EstrogenClientPaths.THIGH_HIGH_ITEM_LOCATION)) {
            return new ForgeThighHighItemModel(getModel(modelLocation));
        }
        return getModel(modelLocation);
    }


}
