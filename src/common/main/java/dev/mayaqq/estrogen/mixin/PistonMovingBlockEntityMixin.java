package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.mayaqq.estrogen.content.blocks.FiltratedHorseUrineCauldron;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PistonMovingBlockEntity.class)
public abstract class PistonMovingBlockEntityMixin {

    @ModifyArg(
            method = "finalTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;updateFromNeighbourShapes(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
            ),
            index = 0
    )
    private BlockState modify(BlockState movedState) {
        if (movedState.getBlock() instanceof FiltratedHorseUrineCauldron block) {
            return block.progress(movedState, ((PistonMovingBlockEntity) (Object) this).getLevel());
        } else {
            return movedState;
        }
    }

    @ModifyArg(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;updateFromNeighbourShapes(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
            ),
            index = 0
    )
    private static BlockState modifySecond(BlockState movedState, @Local(argsOnly = true) Level level) {
        if (movedState.getBlock() instanceof FiltratedHorseUrineCauldron block) {
            return block.progress(movedState, level);
        } else {
            return movedState;
        }
    }
}
