package dev.mayaqq.estrogen.registry.blocks;

import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.blockEntities.DreamBlockEntity;
import dev.mayaqq.estrogen.registry.effects.EstrogenEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
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
        return Shapes.block();
    }

    // checks every block the player intersects with (min 2, max 12)
    public static boolean isInDreamBlock(Player player) {
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
        // this returns true when the player touches the bottom, western or northern side of a dream block
        // due to the implementation of betweenClosedStream(AABB aabb) >:(
        // ergo workaround (that i hate) above. (it could've been so simple :'( )
        // return BlockPos.betweenClosedStream(playerAABB).anyMatch(
        //         pos -> player.level().getBlockState(pos).getBlock() instanceof DreamBlock
        // );
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

            // if player hits a wall while inside dream blocks, make them bounce
            // Vec3 movement = player.getDeltaMovement();
            // if (movement.x() == 0 && lookAngle.x() != 0) lookAngle = lookAngle.multiply(-1, 1, 1);
            // if (movement.y() == 0 && lookAngle.y() != 0) lookAngle = lookAngle.multiply(1, -1, 1);
            // if (movement.z() == 0 && lookAngle.z() != 0) lookAngle = lookAngle.multiply(1, 1, -1);

            player.setDeltaMovement(lookAngle.scale(2));
        }
    }
}
