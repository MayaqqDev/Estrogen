@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.blockEntities.DreamCatcherBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LanternBlock
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import kotlin.reflect.KClass

class DreamCatcherBlock(properties: Properties) : BaseEntityBlock(properties), BlockEntityBlock<DreamCatcherBlockEntity> {

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return canSupportCenter(level, pos.relative(Direction.UP), Direction.DOWN)
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        return if (Direction.UP == direction && !state.canSurvive(level, pos))
            Blocks.AIR.defaultBlockState()
        else
            super.updateShape(state, direction, neighborState, level, pos, neighborPos)
    }

    override val blockEntityClass: KClass<out DreamCatcherBlockEntity> = DreamCatcherBlockEntity::class
    override fun getBlockEntityType(): BlockEntityType<out DreamCatcherBlockEntity> = EstrogenBlockEntities.DreamCatcher
}