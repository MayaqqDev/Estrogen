package dev.mayaqq.estrogen.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluid;

public class ClientPlatform {
    @ExpectPlatform
    public static void fluidRenderLayerMap(Fluid fluid, RenderType type) {
        throw new AssertionError();
    }
}
