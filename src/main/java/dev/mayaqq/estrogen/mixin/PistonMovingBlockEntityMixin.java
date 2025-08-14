package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.mayaqq.estrogen.content.blocks.FiltratedHorseUrineCauldron;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntityMixin {
    @WrapOperation(
            method = "<init>(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;ZZ)V",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.PUTFIELD,
                    target = "Lnet/minecraft/world/level/block/piston/PistonMovingBlockEntity;movedState:Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private void modify(PistonMovingBlockEntity entity, BlockState original, Operation<BlockState> operation) {
        if (original.getBlock() instanceof FiltratedHorseUrineCauldron block) {
            operation.call(entity, block.progress(original));
        } else {
            operation.call(entity, original);
        }
    }
}
