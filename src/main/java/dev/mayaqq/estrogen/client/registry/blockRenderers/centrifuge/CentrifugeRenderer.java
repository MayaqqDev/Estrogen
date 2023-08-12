package dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

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
        FluidStack fluidStack = new FluidStack(EstrogenFluids.LIQUID_ESTROGEN.still(), 10000);
        ms.push();
        FluidRenderer.renderFluidBox(fluidStack, 0, 0, 0, 16, 16, 16, buffer, ms, light, false);
        ms.pop();
    }
}