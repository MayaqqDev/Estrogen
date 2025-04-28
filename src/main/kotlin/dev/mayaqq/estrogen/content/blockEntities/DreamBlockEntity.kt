package dev.mayaqq.estrogen.content.blockEntities

import dev.mayaqq.cynosure.level.BlockUpdateListener
import dev.mayaqq.cynosure.level.addUpdateListener
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import java.util.EnumMap

class DreamBlockEntity(p0: BlockEntityType<*>, p1: BlockPos, p2: BlockState) : BlockEntity(p0, p1, p2), BlockUpdateListener {

    val isPersistent: Boolean
        get() = blockState.getValue(DreamBlock.PERSISTENT)

    private val connection: EnumMap<Direction, Boolean> = EnumMap(Direction::class.java)

    override val listenedPositions: Iterable<BlockPos>
        get() = BlockPos.betweenClosed(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))

    override fun onBlockUpdate(level: Level, pos: BlockPos, state: BlockState) {
        updateState()
    }

    override fun shouldRemove(): Boolean = isRemoved

    override fun setLevel(p0: Level) {
        super.setLevel(p0)
        p0.addUpdateListener(this)
        if (p0 != null) {
            for (direction in Direction.entries)
                connection[direction] = p0.getBlockEntity(blockPos.relative(direction))?.type == EstrogenBlockEntities.DREAM_BLOCK
        }
    }

    fun updateState() {
        this.level?.apply {
            for (direction in Direction.entries)
                connection[direction] = getBlockEntity(blockPos.relative(direction))?.type == EstrogenBlockEntities.DREAM_BLOCK
        }
    }

    fun isTouchingDreamBlock(direction: Direction): Boolean = connection[direction]!!

}