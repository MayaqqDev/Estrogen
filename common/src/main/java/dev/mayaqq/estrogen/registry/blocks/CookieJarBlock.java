package dev.mayaqq.estrogen.registry.blocks;

import dev.mayaqq.estrogen.registry.EstrogenAdvancementCriteria;
import dev.mayaqq.estrogen.registry.EstrogenSoundTypes;
import dev.mayaqq.estrogen.registry.EstrogenSounds;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class CookieJarBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    private static final BooleanProperty WATERLOGGED;
    private static final VoxelShape BOUNDING_BOX = makeShape();

    public CookieJarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof CookieJarBlockEntity cookieJarBlockEntity)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }

        ItemStack handItem = player.getItemInHand(hand);

        if (!handItem.isEmpty()) {
            if (cookieJarBlockEntity.canAddItem(handItem)) {
                // adding item to jar
                cookieJarBlockEntity.add1Item(handItem);
                if (!player.isCreative()) handItem.shrink(1);

                player.awardStat(Stats.ITEM_USED.get(handItem.getItem()));
                level.playSound(null, pos, EstrogenSounds.JAR_INSERT.get(), SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * ((float) cookieJarBlockEntity.getCount() / 512));
                if (level instanceof ServerLevel serverLevel) {
                    EstrogenAdvancementCriteria.INSERT_JAR.trigger((ServerPlayer) player);
                    serverLevel.sendParticles(ParticleTypes.CRIT, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
                }
            } else {
                // jar was full, couldn't add item to jar
                level.playSound(null, pos, EstrogenSounds.JAR_FULL.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        } else {
            ItemStack itemStack = cookieJarBlockEntity.remove1Item();
            if (!itemStack.isEmpty()) {
                // removing item from jar
                level.playSound(null, pos, EstrogenSounds.JAR_INSERT.get(), SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * ((float) cookieJarBlockEntity.getCount() / 512));
                if (level instanceof ServerLevel) {
                    player.getInventory().placeItemBackInInventory(itemStack);
                }
            } else {
                // jar was empty, couldn't remove item from jar
            }
        }

        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return BOUNDING_BOX;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CookieJarBlockEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean bl) {
        if (!state.is(newState.getBlock())) {
            Containers.dropContents(level, blockPos, (CookieJarBlockEntity)level.getBlockEntity(blockPos));
        }
        super.onRemove(state, level, blockPos, newState, bl);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public SoundType getSoundType(BlockState blockState) {
        return EstrogenSoundTypes.COOKIE_JAR;
    }

    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, blockPos)) {
            level.destroyBlock(blockPos, true, projectile);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        // Cork
        shape = Shapes.join(shape, Shapes.box(0.375, 0.625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.AND);
        // Big lid shape
        shape = Shapes.join(shape, Shapes.box(0.1875 + 0.0625, 0.6875, 0.1875 + 0.0625, 0.8125 - 0.0625, 0.75, 0.8125 - 0.0625), BooleanOp.OR);
        // first part
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.625, 0.3125, 0.6875, 0.6875, 0.6875), BooleanOp.OR);
        // Main square
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.625, 0.8125), BooleanOp.OR);

        return shape;
    }
}
