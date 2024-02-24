package dev.mayaqq.estrogen.platform.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.fluids.FluidStack;

public class EstrogenFluidRendererImpl {
    public static void renderFluid(FluidHolder fluid, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax, MultiBufferSource buffer, PoseStack ms, int light, boolean renderBottom) {
        FluidStack fluidStack = new FluidStack(fluid.getFluid(), (int) fluid.getFluidAmount(), fluid.getCompound());
        FluidRenderer.renderFluidBox(fluidStack, xMin, yMin, zMin, xMax, yMax, zMax, buffer, ms, light, renderBottom);
    }
}
