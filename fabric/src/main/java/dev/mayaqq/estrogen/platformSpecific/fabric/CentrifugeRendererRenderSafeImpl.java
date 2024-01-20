package dev.mayaqq.estrogen.platformSpecific.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("UnstableApiUsage")
public class CentrifugeRendererRenderSafeImpl {
    @org.jetbrains.annotations.Contract
    public static void renderSafe(CentrifugeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Level level = be.getLevel();
        if (level == null || !level.isClientSide) return;
        BlockEntity up = getBlockEntity(level, be.getBlockPos().above());
        BlockEntity down = getBlockEntity(level, be.getBlockPos().below());
        if (up != null) {
            Storage<FluidVariant> storageUp = FluidStorage.SIDED.find(be.getLevel(), up.getBlockPos(), Direction.DOWN);
            if (storageUp == null) return;
            FluidStack to = getFluid(storageUp);
            if (to != null) {
                renderFluidSafe(to, 0.01F, 0.71F, 0.01F, 0.99F, 0.97F, 0.99F, buffer, ms, light, false);
            }
        }
        if (down != null) {
            Storage<FluidVariant> storageDown = FluidStorage.SIDED.find(be.getLevel(), down.getBlockPos(), Direction.UP);
            if (storageDown == null) return;
            FluidStack from = getFluid(storageDown);
            if (from != null) {
                renderFluidSafe(from, 0.01F, 0.01F, 0.01F, 0.99F, 0.3F, 0.99F, buffer, ms, light, false);
            }
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

    public static FluidStack getFluid(Storage<FluidVariant> storage) {
        try {
            return TransferUtil.getAllFluids(storage).get(0);
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
