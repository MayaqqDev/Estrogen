package dev.mayaqq.estrogen.platformSpecific.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

public class CentrifugeRendererRenderSafeImpl {
    @org.jetbrains.annotations.Contract
    public static void renderSafe(CentrifugeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Level level = be.getLevel();
        if (level == null || !level.isClientSide) return;
        BlockEntity up = getBlockEntity(level, be.getBlockPos().above());
        BlockEntity down = getBlockEntity(level, be.getBlockPos().below());

        if (up != null) {
            up.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
                FluidStack fluid = handler.getFluidInTank(0);
                if (fluid.isEmpty()) return;
                renderFluidSafe(fluid, 0.01F, 0.71F, 0.01F, 0.99F, 0.97F, 0.99F, buffer, ms, light, false);
            });
        }
        if (down != null) {
            down.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
                FluidStack fluid = handler.getFluidInTank(0);
                if (fluid.isEmpty()) return;
                renderFluidSafe(fluid, 0.01F, 0.01F, 0.01F, 0.99F, 0.3F, 0.99F, buffer, ms, light, false);
            });
        }
    }

    public static BlockEntity getBlockEntity(Level level, BlockPos pos) {
        try {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof FluidTankBlockEntity tank) {
                return tank.getControllerBE();
            } else {
                return be;
            }
        } catch (Exception ignored) {
            return null;
        }
    }

    public static void renderFluidSafe(FluidStack fluidStack, float xMin, float yMin, float zMin, float xMax, float yMax, float zMax, MultiBufferSource buffer, PoseStack ms, int light, boolean renderBottom) {
        try {
            FluidRenderer.renderFluidBox(fluidStack, xMin, yMin, zMin, xMax, yMax, zMax, buffer, ms, light, renderBottom);
        } catch (Exception ignored) {}
    }
}
