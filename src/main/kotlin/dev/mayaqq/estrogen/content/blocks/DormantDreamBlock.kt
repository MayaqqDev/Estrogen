@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenSounds
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.AbstractGlassBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty

@Deprecated("Becoming part of DreamBlock")
class DormantDreamBlock(properties: Properties) : AbstractGlassBlock(properties) {
    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return defaultBlockState().setValue(POWERED, context.level.hasNeighborSignal(context.clickedPos))
    }

    override fun isRandomlyTicking(state: BlockState): Boolean {
        return true
    }

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        neighborBlock: Block,
        neighborPos: BlockPos,
        movedByPiston: Boolean
    ) {
        if (!level.isClientSide) {
            val bl = state.getValue(POWERED)
            if (bl != level.hasNeighborSignal(pos)) {
                if (bl) {
                    level.scheduleTick(pos, this, 4)
                } else {
                    level.setBlock(pos, state.cycle(POWERED), 2)
                }
            }
        }
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (state.getValue(POWERED) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(POWERED), 2)
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(POWERED)
    }

    init {
        this.registerDefaultState(defaultBlockState().setValue(POWERED, false))
    }

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (level.isNight && state.getValue(POWERED)) convert(state, level, pos, random)
    }

    fun convert(state: BlockState?, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        level.playSound(
            null,
            pos,
            EstrogenSounds.DREAM_BLOCK_DORMANT_BREAK,
            SoundSource.BLOCKS,
            random.nextFloat() * 0.1f + 0.9f,
            random.nextFloat() * 0.4f + 0.8f
        )
        level.setBlockAndUpdate(pos, EstrogenBlocks.DREAM_BLOCK.defaultBlockState())
    }

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return false
    }

    override fun getLightBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Int {
        return level.maxLightLevel
    }

    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }
}
