package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.estrogen.utils.transfer.TransferHelper
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import kotlin.jvm.optionals.getOrNull

class TransferBlock(properties: Properties, val new: ResourceLocation) : Block(properties.randomTicks()) {
    override fun isRandomlyTicking(state: BlockState): Boolean = true

    override fun neighborChanged(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        neighborBlock: Block,
        neighborPos: BlockPos,
        movedByPiston: Boolean
    ) { change(level, pos) }

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) = change(level, pos)

    fun change(level: Level, pos: BlockPos) {
        if (isModLoaded("createestrogen")) {
            BuiltInRegistries.BLOCK.getOptional(new).getOrNull()?.let {
                level.setBlockAndUpdate(pos, this.defaultBlockState())
            }
        } else TransferHelper.message(level)
    }
}