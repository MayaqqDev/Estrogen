package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.mayaqq.estrogen.content.blocks.FiltratedHorseUrineCauldron;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntityMixin {
    @WrapOperation(
            method = "finalTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;updateFromNeighbourShapes(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState modify(BlockState movedState, LevelAccessor level, BlockPos pos, Operation<BlockState> original) {
        if (movedState.getBlock() instanceof FiltratedHorseUrineCauldron block) {
            return original.call(block.progress(movedState, level), level, pos);
        } else {
            return original.call(movedState, level, pos);
        }
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;updateFromNeighbourShapes(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private static BlockState modifySecond(BlockState movedState, LevelAccessor level, BlockPos pos, Operation<BlockState> original) {
        if (movedState.getBlock() instanceof FiltratedHorseUrineCauldron block) {
            return original.call(block.progress(movedState, level), level, pos);
        } else {
            return original.call(movedState, level, pos);
        }
    }
}
