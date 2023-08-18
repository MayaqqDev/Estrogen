package dev.mayaqq.estrogen.registry.common.blocks;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import dev.mayaqq.estrogen.registry.common.EstrogenBlockEntities;
import dev.mayaqq.estrogen.registry.common.blockEntities.CentrifugeBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class CentrifugeBlock extends KineticBlock implements IBE<CentrifugeBlockEntity>, ICogWheel, IWrenchable {

    public CentrifugeBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public boolean canPlaceAt(BlockState p_196260_1_, WorldView p_196260_2_, BlockPos p_196260_3_) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.fullCube();
    }

    @Override
    public void onPlaced(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.onPlaced(pLevel, pPos, pState, pPlacer, pStack);
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView reader, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState pState, Direction pFacing, BlockState pFacingState, WorldAccess pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pState;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.of(256);
    }

    @Override
    public boolean isSmallCog() {
        return true;
    }

    @Override
    public Class<CentrifugeBlockEntity> getBlockEntityClass() {
        return CentrifugeBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CentrifugeBlockEntity> getBlockEntityType() {
        return  EstrogenBlockEntities.CENTRIFUGE.get();
    }
}
