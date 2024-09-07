package dev.mayaqq.estrogen.registry.blocks;

import dev.mayaqq.estrogen.client.features.dash.ClientDash;
import dev.mayaqq.estrogen.features.dash.CommonDash;
import dev.mayaqq.estrogen.registry.EstrogenBlockEntities;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uwu.serenity.critter.utils.BEBlock;

@SuppressWarnings("deprecation")
public class DreamBlock extends BaseEntityBlock implements BEBlock<DreamBlockEntity> {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    public DreamBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
            .setValue(UP, false)
            .setValue(DOWN, false)
            .setValue(NORTH, false)
            .setValue(SOUTH, false)
            .setValue(EAST, false)
            .setValue(WEST, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }


    @Override
    public boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity instanceof Player player && (CommonDash.isPlayerDashing(player.getUUID()) || isInDreamBlock(player))){
                return Shapes.empty();
            }
        }
        return Shapes.block();
    }

    /**
     * Checks for if the player is colliding with a dream block.
     */
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof DreamBlockEntity dream && level.isClientSide()) {
            dream.updateTexture(direction.getAxis() != Direction.Axis.Y);
        }

        if(neighborState.is(this)) {
            return state.setValue(directionProperty(direction), true);
        } else {
            return state.setValue(directionProperty(direction), false);
        }
    }

    public static BooleanProperty directionProperty(Direction direction) {
        return switch (direction) {
            case UP -> UP;
            case DOWN -> DOWN;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
        };
    }

    public static boolean isInDreamBlock(Player player) {
        if (player.isSpectator()) return false;

        AABB playerAABB = player.getBoundingBox();
        BlockPos minPos = BlockPos.containing(playerAABB.minX, playerAABB.minY, playerAABB.minZ);
        BlockPos maxPos = new BlockPos(
                Mth.ceil(playerAABB.maxX) - 1,
                Mth.ceil(playerAABB.maxY) - 1,
                Mth.ceil(playerAABB.maxZ) - 1
        );
        return BlockPos.betweenClosedStream(minPos, maxPos).anyMatch(
                pos -> player.level().getBlockState(pos).getBlock() instanceof DreamBlock
        );

        // can't use betweenClosedStream because it also sometimes includes blocks that the player
        // is touching the face of, but not colliding with. >:(
        //return BlockPos.betweenClosedStream(playerAABB).anyMatch(
        //        pos -> player.level().getBlockState(pos).getBlock() instanceof DreamBlock
        //);
    }

    public static Vec3 lookAngle = null;

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.resetFallDistance();
        if (entity instanceof Player player && level.isClientSide) {
            ClientDash.refresh(player);
            if (lookAngle == null) {
                lookAngle = player.getLookAngle();
            }

            // if player hits a wall while inside dream blocks, make them bounce
            // Vec3 movement = player.getDeltaMovement();
            // if (movement.x() == 0 && lookAngle.x() != 0) lookAngle = lookAngle.multiply(-1, 1, 1);
            // if (movement.y() == 0 && lookAngle.y() != 0) lookAngle = lookAngle.multiply(1, -1, 1);
            // if (movement.z() == 0 && lookAngle.z() != 0) lookAngle = lookAngle.multiply(1, 1, -1);

            player.setDeltaMovement(lookAngle.scale(2));
        }
    }

    @Override
    public BlockEntityType<? extends DreamBlockEntity> getBlockEntityType() {
        return EstrogenBlockEntities.DREAM_BLOCK.get();
    }

    @Override
    public Class<? extends DreamBlockEntity> getBlockEntityClass() {
        return DreamBlockEntity.class;
    }
}
