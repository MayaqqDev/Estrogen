package dev.mayaqq.estrogen.mixin.forge;

import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.content.EstrogenFluids;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpongeBlock.class)
public class SpongeBlockMixin {
    @Inject(
            method = "lambda$removeWaterBreadthFirstSearch$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private static void inject(BlockPos center, Level level, BlockState state, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = level.getBlockState(pos);
        FluidState fluidState = level.getFluidState(pos);
        if (fluidState.is(EstrogenFluids.INSTANCE.getHorseUrine().getSource()) || fluidState.is(EstrogenFluids.INSTANCE.getHorseUrine().getFlowing())) {
            level.setBlockAndUpdate(
                    pos,
                    EstrogenFluids.INSTANCE.getFiltratedHorseUrine().getBlock().withPropertiesOf(blockState)
            );
            cir.setReturnValue(true);
        }
    }
}
