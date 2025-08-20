package dev.mayaqq.estrogen.content.blockEntities

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class MemorialBlockEntity(be: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(be, pos, state)