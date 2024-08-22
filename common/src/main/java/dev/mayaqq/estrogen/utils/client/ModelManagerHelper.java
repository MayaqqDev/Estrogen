package dev.mayaqq.estrogen.utils.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public interface ModelManagerHelper {
    static BakedModel getModel(ResourceLocation location) {
        return ((ModelManagerHelper) Minecraft.getInstance().getModelManager()).estrogen$getModel(location);
    }

    BakedModel estrogen$getModel(ResourceLocation location);
}
