package dev.mayaqq.estrogen.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.client.renderer.MultiBufferSource;

public class EstrogenFluidRenderer {
    private EstrogenFluidRenderer() {}

    @ExpectPlatform
    public static void renderFluid(FluidHolder fluid, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax, MultiBufferSource buffer, PoseStack ms, int light, boolean renderBottom) {
        throw new AssertionError();
    }
}
