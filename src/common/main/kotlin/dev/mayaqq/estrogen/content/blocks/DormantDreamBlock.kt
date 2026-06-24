@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.content.EstrogenBlocks
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.AbstractGlassBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

@Deprecated("Becoming part of DreamBlock")
class DormantDreamBlock(properties: Properties) : AbstractGlassBlock(properties) {

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState = EstrogenBlocks.DreamBlock.defaultBlockState()

    override fun isRandomlyTicking(state: BlockState): Boolean = true

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        neighborBlock: Block,
        neighborPos: BlockPos,
        movedByPiston: Boolean
    ) {
        level.setBlockAndUpdate(pos, EstrogenBlocks.DreamBlock.defaultBlockState())
    }

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        level.setBlockAndUpdate(pos, EstrogenBlocks.DreamBlock.defaultBlockState())
    }

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return false
    }

    override fun getLightBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Int {
        return level.maxLightLevel
    }
}
