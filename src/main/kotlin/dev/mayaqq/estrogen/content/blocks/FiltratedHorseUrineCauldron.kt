package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenBlocks
import net.minecraft.core.BlockPos
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.util.RandomSource
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty

class FiltratedHorseUrineCauldron(properties: Properties, interactions: Map<Item, CauldronInteraction>) : LayeredCauldronBlock(properties, {false}, interactions) {
    init {
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1).setValue(PROGRESS, 0))
    }

    fun progress(state: BlockState): BlockState {
        if (pissRandomSource.nextInt(3) == 1) {
            val newState = state.cycle(PROGRESS)
            return if (newState.getValue(PROGRESS) == 5) {
                EstrogenBlocks.LiquidEstrogenCauldron.defaultBlockState().setValue(
                    LEVEL,
                    newState.getValue(LEVEL))
            } else {
                newState
            }
        }
        return state
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(LEVEL)
        builder.add(PROGRESS)
    }

    companion object {
        val PROGRESS: IntegerProperty = IntegerProperty.create("progress", 0, 5)
        val pissRandomSource = RandomSource.create()
    }
}