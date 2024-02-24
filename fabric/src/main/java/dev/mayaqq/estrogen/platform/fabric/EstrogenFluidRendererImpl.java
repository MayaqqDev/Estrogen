package dev.mayaqq.estrogen.platform.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.client.renderer.MultiBufferSource;

public class EstrogenFluidRendererImpl {
    public static void renderFluid(FluidHolder fluid, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax, MultiBufferSource buffer, PoseStack ms, int light, boolean renderBottom) {
        FluidStack fluidStack = new FluidStack(fluid.getFluid(), fluid.getFluidAmount(), fluid.getCompound());
        FluidRenderer.renderFluidBox(fluidStack, xMin, yMin, zMin, xMax, yMax, zMax, buffer, ms, light, renderBottom);
    }
}
