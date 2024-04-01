package dev.mayaqq.estrogen.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class StatePredicates {
    public static boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }
}
