package dev.mayaqq.estrogen.registry.blockEntities;

import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DreamBlockEntity extends BlockEntity {
    public DreamBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public DreamBlockEntity(BlockPos pos, BlockState state) {
        super(EstrogenBlockEntities.DREAM_BLOCK.get(), pos, state);
    }
}
