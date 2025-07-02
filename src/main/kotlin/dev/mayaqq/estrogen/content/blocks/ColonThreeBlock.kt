package dev.mayaqq.estrogen.content.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

//TODO: Hide recipe for this from Recipe Viewers
class ColonThreeBlock(properties: Properties) : Block(properties) {
    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (random.nextInt(10) == 0) {
            val dir = when(random.nextInt(6)) {
                0 -> Direction.UP
                1 -> Direction.DOWN
                2 -> Direction.NORTH
                3 -> Direction.SOUTH
                4 -> Direction.EAST
                5 -> Direction.WEST
                else -> Direction.UP
            }

            val relative = pos.relative(dir)
            if (level.getBlockState(relative).isAir) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState())
                level.setBlockAndUpdate(relative, state)
            }
        }
    }
}