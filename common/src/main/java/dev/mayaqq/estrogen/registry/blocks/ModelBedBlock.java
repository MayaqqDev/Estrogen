package dev.mayaqq.estrogen.registry.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
/*
 * Code and models are taken and modified from the BetterBeds mod by TeamMidnightDust
 * The code falls under the MIT license, thanks TeamMidnightDust!
 */
public class ModelBedBlock extends BedBlock {
    public ModelBedBlock(DyeColor color, Properties properties) {
        super(color, properties);
    }

    public ModelBedBlock(Properties properties) {
        this(null, properties);
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean skipRendering(BlockState state, BlockState neighborState, Direction offset) {
        return neighborState.getBlock() instanceof BedBlock;
    }
}
