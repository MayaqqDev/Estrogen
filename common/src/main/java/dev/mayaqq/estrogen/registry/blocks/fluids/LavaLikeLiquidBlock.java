package dev.mayaqq.estrogen.registry.blocks.fluids;

import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class LavaLikeLiquidBlock extends BotariumLiquidBlock {
    public LavaLikeLiquidBlock(FluidData data, Properties properties) {
        super(data, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.lavaHurt();
    }
}
