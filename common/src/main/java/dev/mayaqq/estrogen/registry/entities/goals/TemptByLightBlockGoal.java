package dev.mayaqq.estrogen.registry.entities.goals;

import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.EnumSet;

public class TemptByLightBlockGoal extends Goal {

    private static final ArrayList<BlockPos> OFFSETS = Util.make(new ArrayList<>(), offsets -> {
        for(int i = 0; (double)i <= 5; i = i > 0 ? -i : 1 - i) {
            for(int j = 0; (double)j < 5; ++j) {
                for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                    for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                        offsets.add(new BlockPos(k,i-1, l));
                    }
                }
            }
        }
    });

    private final MothEntity moth;
    private final double speedModifier;
    private double wantedX;
    private double wantedY;
    private double wantedZ;

    private final int within;

    public TemptByLightBlockGoal(MothEntity moth, double speedModifier, int within) {
        this.moth = moth;
        this.speedModifier = speedModifier;
        this.within = within;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        Level level = this.moth.level();
        if (this.moth.getRandom().nextInt(20) != 0) return false;
        BlockPos mothPos = this.moth.blockPosition();
        BlockPos.MutableBlockPos pos = this.moth.blockPosition().mutable();

        for (BlockPos blockPos : OFFSETS) {
            pos.setWithOffset(mothPos, blockPos);
            BlockState state = level.getBlockState(pos);
            if (state.getLightEmission() > 0) {
                this.wantedX = pos.getX();
                this.wantedY = pos.getY();
                this.wantedZ = pos.getZ();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        BlockPos pos = this.moth.blockPosition();
        BlockPos targetPos = new BlockPos((int) this.wantedX, (int) this.wantedY, (int) this.wantedZ);
        return !this.moth.getNavigation().isDone() && targetPos.distToCenterSqr(pos.getX(), pos.getY(), pos.getZ()) < (double)(this.within * this.within);
    }

    @Override
    public void start() {
        this.moth.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }
}
