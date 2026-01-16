package dev.mayaqq.estrogen.content.blocks.fluid

import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock
import earth.terrarium.botarium.common.registry.fluid.FluidData
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

open class BaseEstrogenLiquidBlock(data: FluidData, properties: Properties, val interactions: Array<FluidInteraction> = emptyArray()) : BotariumLiquidBlock(data, properties) {

    //TODO
    private fun fluidInteraction(level: Level, pos: BlockPos, state: BlockState): Boolean {
        this.interactions.forEach { interaction ->
            POSSIBLE_FLOW_DIRECTIONS.forEach { flowDir ->
                val interactedPos = pos.relative(flowDir.opposite)
                val interactedState = level.getBlockState(interactedPos)
                val interactedFluidState = level.getFluidState(interactedPos)
                interaction.interacted.invoke(interactedPos, interactedState, interactedFluidState)?.let { state ->
                    level.setBlockAndUpdate(pos, state)
                    //fizz
                    level.levelEvent(1501, pos, 0)
                    return false
                }
            }
        }

        return true
    }

    class FluidInteraction(val interacted: (BlockPos, BlockState, FluidState) -> BlockState?)
}