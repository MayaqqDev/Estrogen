package dev.mayaqq.estrogen.content.blockEntities

import dev.mayaqq.estrogen.content.blocks.DreamBlock
import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.longs.LongList
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.SectionPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class DreamBlockEntity(type: BlockEntityType<*>, blockPos: BlockPos, blockState: BlockState) : BlockEntity(type, blockPos, blockState) {

    init {
        CHUNKS.add(SectionPos.asLong(blockPos))
    }

    val isPersistent: Boolean
        get() = blockState.getValue(DreamBlock.PERSISTENT)

    fun isTouchingDreamBlock(direction: Direction): Boolean {
        return DreamBlock.isTouchingDreamBlock(blockState, direction)
    }

    override fun setRemoved() {
        super.setRemoved()
        CHUNKS.remove(SectionPos.asLong(blockPos))
    }

    companion object {
        val CHUNKS: LongList = LongArrayList()
    }
}