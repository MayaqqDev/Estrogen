package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.content.blocks.FiltratedHorseUrineCauldron;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntityMixin {
    @ModifyVariable(
            method = "<init>(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;ZZ)V",
            at = @At(value = "CTOR_HEAD", args="enforce=POST_DELEGATE", ordinal = 2),
            argsOnly = true,
            ordinal = 1
    )
    private BlockState modify(BlockState original) {
        if (original.getBlock() instanceof FiltratedHorseUrineCauldron block) {
            return block.progress(original);
        } else {
            return original;
        }
    }
}
