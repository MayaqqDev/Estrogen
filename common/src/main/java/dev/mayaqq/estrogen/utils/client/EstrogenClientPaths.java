package dev.mayaqq.estrogen.utils.client;

import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class EstrogenClientPaths {
    public static final ResourceLocation THIGH_HIGH_ITEM_LOCATION = new ModelResourceLocation(Estrogen.id("thigh_highs"), "inventory");
    public static final String THIGH_HIGH_MODELS_DIRECTORY = "models/thigh_highs";
    public static final String THIGH_HIGH_ITEM_TEXTURES = "textures/item/thigh_highs";
}
