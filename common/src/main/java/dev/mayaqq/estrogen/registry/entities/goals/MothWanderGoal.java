package dev.mayaqq.estrogen.registry.entities.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

// Stolen Parrot Code
public class MothWanderGoal extends WaterAvoidingRandomFlyingGoal {
    public MothWanderGoal(PathfinderMob pathfinderMob, double d) {
        super(pathfinderMob, d);
    }

    @Override
    @Nullable
    protected Vec3 getPosition() {
        Vec3 vec3 = null;
        if (this.mob.isInWater()) {
            vec3 = LandRandomPos.getPos(this.mob, 15, 15);
        }
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            vec3 = this.getTreePos();
        }
        return vec3 == null ? super.getPosition() : vec3;
    }

    @Nullable
    private Vec3 getTreePos() {
        BlockPos blockPos = this.mob.blockPosition();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
        Iterable<BlockPos> iterable = BlockPos.betweenClosed(Mth.floor(this.mob.getX() - 3.0), Mth.floor(this.mob.getY() - 6.0), Mth.floor(this.mob.getZ() - 3.0), Mth.floor(this.mob.getX() + 3.0), Mth.floor(this.mob.getY() + 6.0), Mth.floor(this.mob.getZ() + 3.0));
        for (BlockPos blockPos2 : iterable) {
            BlockState blockState;
            boolean bl;
            if (blockPos.equals(blockPos2) || !(bl = (blockState = this.mob.level().getBlockState(mutableBlockPos2.setWithOffset(blockPos2, Direction.DOWN))).getLightEmission() > 0 || !this.mob.level().isEmptyBlock(blockPos2) || !this.mob.level().isEmptyBlock(mutableBlockPos.setWithOffset(blockPos2, Direction.UP)))) continue;
            return Vec3.atBottomCenterOf(blockPos2);
        }
        return null;
    }
}
