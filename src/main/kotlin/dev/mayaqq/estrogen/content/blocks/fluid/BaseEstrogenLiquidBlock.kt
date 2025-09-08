package dev.mayaqq.estrogen.content.blocks.fluid

import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock
import earth.terrarium.botarium.common.registry.fluid.FluidData
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

open class BaseEstrogenLiquidBlock(data: FluidData, properties: Properties, val interactions: Array<FluidInteraction> = emptyArray()) : BotariumLiquidBlock(data, properties) {
    constructor(data: FluidData, properties: Properties, vararg interactions: FluidInteraction): this(data, properties, arrayOf(*interactions))

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, old: BlockState, movedByPiston: Boolean) {
        if (this.fluidInteraction(level, pos, state)) {
            level.scheduleTick(pos, state.fluidState.type, this.fluid.getTickDelay(level))
        }
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        neighborBlock: Block,
        neighborPos: BlockPos,
        movedByPiston: Boolean
    ) {
        if (this.fluidInteraction(level, pos, state)) {
            level.scheduleTick(pos, state.fluidState.type, this.fluid.getTickDelay(level))
        }
    }

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