package dev.mayaqq.estrogen.registry.blocks;

import dev.mayaqq.estrogen.registry.EstrogenTags;
import dev.mayaqq.estrogen.registry.blockEntities.CookieJarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
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
    public static final IntegerProperty STORED_COOKIES;

    public CookieJarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(WATERLOGGED, false)
                        .setValue(STORED_COOKIES, 0)
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

    private static void setStoredCookies(Level level, CookieJarBlockEntity blockEntity, BlockState state) {
        int amount = blockEntity.getTheItem().getCount() == 0 ? 0 : Math.min(Math.floorDiv(blockEntity.getTheItem().getCount(), 16) + 1, 5);
        level.setBlock(blockEntity.getBlockPos(), state.setValue(STORED_COOKIES, amount), 3);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.getItemInHand(hand) == ItemStack.EMPTY) {
            if (blockEntity instanceof CookieJarBlockEntity cookieJarBlockEntity) {
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                if (!level.isClientSide) {
                    ItemStack itemStack2 = cookieJarBlockEntity.getTheItem();
                    if (!itemStack2.isEmpty()) {
                        level.playSound(null, pos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * itemStack2.getCount());
                        itemStack2.shrink(1);
                        player.getInventory().placeItemBackInInventory(itemStack2.copyWithCount(1));
                        setStoredCookies(level, cookieJarBlockEntity, state);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                return InteractionResult.PASS;
            }
        } else {
            if (blockEntity instanceof CookieJarBlockEntity cookieJarBlockEntity) {
                if (level.isClientSide) {
                    return InteractionResult.CONSUME;
                } else {
                    ItemStack itemStack2 = cookieJarBlockEntity.getTheItem();
                    if (!itemStack.isEmpty() && (itemStack2.isEmpty() || ItemStack.isSameItem(itemStack2, itemStack) && itemStack2.getCount() < itemStack2.getMaxStackSize()) && itemStack.is(EstrogenTags.Items.COOKIES)) {
                        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                        ItemStack itemStack3 = player.isCreative() ? itemStack.copyWithCount(1) : itemStack.split(1);
                        float f;
                        if (cookieJarBlockEntity.isEmpty()) {
                            cookieJarBlockEntity.setTheItem(itemStack3);
                            f = (float)itemStack3.getCount() / (float)itemStack3.getMaxStackSize();
                        } else {
                            itemStack2.grow(1);
                            f = (float)itemStack2.getCount() / (float)itemStack2.getMaxStackSize();
                        }

                        setStoredCookies(level, cookieJarBlockEntity, state);

                        level.playSound(null, pos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1.0F, 0.7F + 0.5F * f);
                        if (level instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel)level;
                            serverLevel.sendParticles(ParticleTypes.CRIT, (double)pos.getX() + 0.5, (double)pos.getY() + 1.2, (double)pos.getZ() + 0.5, 7, 0.0, 0.0, 0.0, 0.0);
                        }

                        cookieJarBlockEntity.setChanged();
                        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                        setStoredCookies(level, cookieJarBlockEntity, state);
                        return InteractionResult.SUCCESS;
                    } else {
                        return InteractionResult.FAIL;
                    }
                }
            }
        }
        return InteractionResult.CONSUME;
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
        builder.add(new Property[]{WATERLOGGED, STORED_COOKIES});
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CookieJarBlockEntity(blockPos, blockState);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            Containers.dropContents(level, blockPos, (CookieJarBlockEntity)level.getBlockEntity(blockPos));
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return (Boolean)blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public SoundType getSoundType(BlockState blockState) {
        return SoundType.GLASS;
    }

    @Override
    public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, blockPos)) {
            level.destroyBlock(blockPos, true, projectile);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
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
        STORED_COOKIES = IntegerProperty.create("stored_cookies", 0, 5);
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
