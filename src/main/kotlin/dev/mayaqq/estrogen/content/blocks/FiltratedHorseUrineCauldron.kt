package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.modules.anyModuleHasFlag
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty

class FiltratedHorseUrineCauldron(properties: Properties, interactions: CauldronInteraction.InteractionMap) : LayeredCauldronBlock(Biome.Precipitation.NONE, interactions, properties) {
    init {
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1).setValue(PROGRESS, 0))
    }

    fun progress(state: BlockState, level: LevelAccessor): BlockState {
        if (level is Level && level.recipeManager.getAllRecipesFor(EstrogenRecipes.LIQUID_ESTROGEN_CAULDRON.value).any { !it.value().enabled }) return state
        if (anyModuleHasFlag(EstrogenFlag.DISABLES_CAULDRON_ESTROGEN)) return state
        if (level.random.nextInt(3) == 1 && !level.isClientSide) {
            val newState = state.cycle(PROGRESS)
            return if (newState.getValue(PROGRESS) == 5) {
                EstrogenBlocks.LiquidEstrogenCauldron.value!!.defaultBlockState().setValue(
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
    }
}