package dev.mayaqq.estrogen.platform.forge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluid;

public class ClientPlatformImpl {
    public static void fluidRenderLayerMap(Fluid fluid, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(fluid, type);
    }
}
