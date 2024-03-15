package dev.mayaqq.estrogen.registry.blocks;

import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class DreamBlock extends BaseEntityBlock {
    public DreamBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DreamBlockEntity(pos, state);
    }

    @Override
    public boolean canBeReplaced(BlockState state, Fluid fluid) {
        return false;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity instanceof Player player){
                if (EstrogenEffect.dashing.getOrDefault(player.getUUID(), 0) > 0 || isInDreamBlock(player)) {
                    return Shapes.empty();
                }
            }
        }

        return state.getShape(level, pos);
    }

    public static boolean isInDreamBlock(Player player) {
        BlockPos pos = player.blockPosition();
        BlockPos headpos = pos.above();
        return player.level().getBlockState(pos).getBlock() instanceof DreamBlock || player.level().getBlockState(headpos).getBlock() instanceof DreamBlock;
    }

    public static Vec3 lookAngle = null;

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entity.resetFallDistance();
        if (entity instanceof Player player && level.isClientSide) {
            EstrogenEffect.currentDashes = (short) player.getAttributeValue(EstrogenAttributes.DASH_LEVEL.get());
            if (lookAngle == null) {
                lookAngle = player.getLookAngle();
            }
            player.setDeltaMovement(lookAngle.x * 2, lookAngle.y * 2, lookAngle.z * 2);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == Items.GLASS_BOTTLE) {
            if (!level.isClientSide) {
                if (!player.isCreative()) stack.shrink(1);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.getInventory().placeItemBackInInventory(new ItemStack(EstrogenItems.DREAM_BOTTLE.get()));
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
}
