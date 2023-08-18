package dev.mayaqq.estrogen.registry.client.registry.blockRenderers.centrifuge;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import dev.mayaqq.estrogen.registry.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

@SuppressWarnings("UnstableApiUsage")
public class CentrifugeRenderer extends KineticBlockEntityRenderer<CentrifugeBlockEntity> {

    public CentrifugeRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(CentrifugeBlockEntity be, BlockState state) {
        return CachedBufferer.partial(EstrogenRenderer.CENTRIFUGE_COG, state);
    }

    @Override
    protected void renderSafe(CentrifugeBlockEntity be, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        Storage<FluidVariant> storageUp = FluidStorage.SIDED.find(be.getWorld(), be.getPos().up(), Direction.DOWN);
        Storage<FluidVariant> storageDown = FluidStorage.SIDED.find(be.getWorld(), be.getPos().down(), Direction.UP);
        if (storageUp == null || storageDown == null) return;
        FluidStack from = null;
        FluidStack to = null;
        try {
            from = TransferUtil.getAllFluids(storageDown).get(0);
            to = TransferUtil.getAllFluids(storageUp).get(0);
        } catch (Exception e) {
            return;
        }
        if (from == null || to == null) return;
        ms.push();
        FluidRenderer.renderFluidBox(from, 0.01F, 0.01F, 0.01F, 0.99F, 0.3F, 0.99F, buffer, ms, light, false);
        FluidRenderer.renderFluidBox(to, 0.01F, 0.71F, 0.01F, 0.99F, 0.97F, 0.99F, buffer, ms, light, false);
        ms.pop();
    }
}