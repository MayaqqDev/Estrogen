package dev.mayaqq.estrogen.mixin.compat.sable;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.content.EstrogenBlocks;
import dev.ryanhcode.sable.physics.impl.SubLevelEntityCollisionContext;
import dev.ryanhcode.sable.util.LevelAccelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "dev.ryanhcode.sable.sublevel.entity_collision.SubLevelEntityCollision")
public class SubLevelEntityCollisionMixin {
    @ModifyReturnValue(
            method = "getSubLevelEntityCollisionShape",
            at = @At("RETURN"),
            require = 0
    )
    private static VoxelShape modifyDreamBlockCollisionContext(
            VoxelShape original,
            @Local(argsOnly = true) BlockState state,
            @Local(argsOnly = true) LevelAccelerator level,
            @Local(argsOnly = true) BlockPos pos,
            @Local(argsOnly = true) Entity entity
    ) {
        if (state.is(EstrogenBlocks.INSTANCE.getDreamBlock().get())) {
            return state.getCollisionShape(level, pos, new SubLevelEntityCollisionContext(entity));
        }
        return original;
    }
}
