package dev.mayaqq.estrogen.client.registry.blockRenderers.centrifuge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.platform.EstrogenFluidRenderer;
import dev.mayaqq.estrogen.registry.blockEntities.CentrifugeBlockEntity;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CentrifugeRenderer extends KineticBlockEntityRenderer<CentrifugeBlockEntity> {

    public CentrifugeRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(CentrifugeBlockEntity be, BlockState state) {
        return CachedBufferer.partial(EstrogenRenderer.CENTRIFUGE_COG, state);
    }

    @Override
    protected void renderSafe(CentrifugeBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        Level level = be.getLevel();
        if (level == null || !level.isClientSide) return;
        BlockEntity up = CentrifugeRenderer.getBlockEntity(level, be.getBlockPos().above());
        BlockEntity down = CentrifugeRenderer.getBlockEntity(level, be.getBlockPos().below());
        renderFluid(up, true, buffer, ms, light);
        renderFluid(down, false, buffer, ms, light);
    }

    private static void renderFluid(BlockEntity blockEntity, boolean isTop, MultiBufferSource buffer, PoseStack ms, int light) {
        if (blockEntity != null) {
            FluidContainer container = FluidContainer.of(blockEntity, null);
            if (container != null) {
                FluidHolder fluid = container.getFirstFluid();
                if (fluid != null && !fluid.isEmpty()) {
                    float yMin = isTop ? 0.71F : 0.01F;
                    float yMax = isTop ? 0.97F : 0.3F;
                    EstrogenFluidRenderer.renderFluid(fluid, 0.01F, yMin, 0.01F, 0.99F, yMax, 0.99F, buffer, ms, light, false);
                }
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
}