package dev.mayaqq.estrogen.platformSpecific.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Direction;

public class CentrifugeRendererRenderSafeImpl {
    @org.jetbrains.annotations.Contract
    public static void renderSafe(CentrifugeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Storage<FluidVariant> storageUp = FluidStorage.SIDED.find(be.getLevel(), be.getBlockPos().above(), Direction.DOWN);
        Storage<FluidVariant> storageDown = FluidStorage.SIDED.find(be.getLevel(), be.getBlockPos().below(), Direction.UP);
        if (storageUp == null || storageDown == null) return;
        FluidStack from;
        FluidStack to;
        try {
            from = TransferUtil.getAllFluids(storageDown).get(0);
            to = TransferUtil.getAllFluids(storageUp).get(0);
        } catch (Exception e) {
            return;
        }
        if (from == null || to == null) return;
        ms.pushPose();
        FluidRenderer.renderFluidBox(from, 0.01F, 0.01F, 0.01F, 0.99F, 0.3F, 0.99F, buffer, ms, light, false);
        FluidRenderer.renderFluidBox(to, 0.01F, 0.71F, 0.01F, 0.99F, 0.97F, 0.99F, buffer, ms, light, false);
        ms.popPose();
    }
}
