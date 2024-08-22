package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.interfaces.ModelManagerHelper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ModelManager.class)
public class ModelManagerMixin implements ModelManagerHelper {
    @Shadow private Map<ResourceLocation, BakedModel> bakedRegistry;

    @Override
    public BakedModel estrogen$getModel(ResourceLocation location) {
        return bakedRegistry.get(location);
    }
}
