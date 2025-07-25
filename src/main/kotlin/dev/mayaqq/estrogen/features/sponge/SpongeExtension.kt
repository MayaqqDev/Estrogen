package dev.mayaqq.estrogen.features.sponge

import dev.mayaqq.estrogen.content.EstrogenFluids.FiltratedHorseUrine
import dev.mayaqq.estrogen.content.EstrogenFluids.HorseUrine
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

object SpongeExtension {
    fun onSuckUp(
        blockState: BlockState,
        fluidState: FluidState,
        center: BlockPos,
        level: Level,
        pos: BlockPos,
        cir: CallbackInfoReturnable<Boolean>
    ) {
        if ((fluidState.`is`(HorseUrine.source)  || fluidState.`is`(HorseUrine.flowing))
            && blockState.hasProperty(BotariumLiquidBlock.LEVEL)) {
            //TODO: Particles
            level.setBlockAndUpdate(pos, FiltratedHorseUrine.block.withPropertiesOf(blockState))
            cir.returnValue = true
        }
    }
}