package dev.mayaqq.estrogen.BlockEntities;

import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import dev.mayaqq.estrogen.registry.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;


public class CentrifugeBlockEntity extends BasinTileEntity {
    public CentrifugeBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.CENTRIFUGE_BLOCK_ENTITY, pos, state);
    }
}
