package dev.mayaqq.estrogen.content.blockEntities

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.features.TextRendererFeatures
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import uwu.serenity.kritter.stdlib.TickableBlockEntity

class DreamBlockEntity(p0: BlockEntityType<*>, p1: BlockPos, p2: BlockState) : BlockEntity(p0, p1, p2) {

    val isPersistent: Boolean
        get() = blockState.getValue(DreamBlock.PERSISTENT)

    fun isTouchingDreamBlock(direction: Direction): Boolean = blockState.getValue(DreamBlock.directionProperty(direction))

}