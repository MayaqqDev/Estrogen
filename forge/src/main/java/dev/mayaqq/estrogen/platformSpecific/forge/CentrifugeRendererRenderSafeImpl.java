package dev.mayaqq.estrogen.platformSpecific.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

public class CentrifugeRendererRenderSafeImpl {
    @org.jetbrains.annotations.Contract
    public static void renderSafe(CentrifugeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Level level = be.getLevel();
        if (level == null) return;
        BlockEntity up = level.getBlockEntity(be.getBlockPos().above());
        BlockEntity down = level.getBlockEntity(be.getBlockPos().below());
        if (up == null || down == null) return;

        if (!up.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent() || !down.getCapability(ForgeCapabilities.FLUID_HANDLER).isPresent()) return;

        FluidStack fluidUp = up.getCapability(ForgeCapabilities.FLUID_HANDLER).map(handler -> handler.getFluidInTank(0)).orElse(null);
        FluidStack fluidDown = down.getCapability(ForgeCapabilities.FLUID_HANDLER).map(handler -> handler.getFluidInTank(0)).orElse(null);

        if (fluidUp == null || fluidDown == null) return;

        ms.pushPose();
        try {
            FluidRenderer.renderFluidBox(fluidDown, 0.01F, 0.01F, 0.01F, 0.99F, 0.3F, 0.99F, buffer, ms, light, false);
            FluidRenderer.renderFluidBox(fluidUp, 0.01F, 0.71F, 0.01F, 0.99F, 0.97F, 0.99F, buffer, ms, light, false);
        } catch (Exception ignored) {}
        ms.popPose();
    }
}
