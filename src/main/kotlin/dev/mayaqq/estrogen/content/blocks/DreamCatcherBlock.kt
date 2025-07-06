@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.blockEntities.DreamCatcherBlockEntity
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.utils.EstrogenColors.getDye
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import kotlin.reflect.KClass


class DreamCatcherBlock(properties: Properties) : HorizontalDirectionalBlock(properties), BlockEntityBlock<DreamCatcherBlockEntity>, EntityBlock {
    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return canSupportCenter(level, pos.relative(Direction.UP), Direction.DOWN)
    }

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return when (state.getValue(FACING)) {
            Direction.NORTH -> SHAPE_NORTH
            Direction.SOUTH -> SHAPE_SOUTH
            Direction.EAST -> SHAPE_EAST
            Direction.WEST -> SHAPE_WEST
            else -> SHAPE_NORTH
        }
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

    companion object {
        @JvmStatic private val SHAPE_NORTH = Shapes.or(
            Shapes.box(0.375, -0.0625, 0.4375, 0.6875, 0.875, 0.5625),
            Shapes.box(0.0625, 0.25, 0.4375, 1.0, 0.5625, 0.5625),
            Shapes.box(0.25, 0.0, 0.4375, 0.8125, 0.8125, 0.5625),
            Shapes.box(0.125, 0.125, 0.4375, 0.9375, 0.6875, 0.5625),
            Shapes.box(0.1875, 0.0625, 0.4375, 0.875, 0.75, 0.5625))
        @JvmStatic private val SHAPE_EAST = Shapes.or(
            Shapes.box(0.4375, -0.0625, 0.375, 0.5625, 0.875, 0.6875),
            Shapes.box(0.4375, 0.25, 0.0625, 0.5625, 0.5625, 1.0),
            Shapes.box(0.4375, 0.0, 0.25, 0.5625, 0.8125, 0.8125),
            Shapes.box(0.4375, 0.125, 0.125, 0.5625, 0.6875, 0.9375),
            Shapes.box(0.4375, 0.0625, 0.1875, 0.5625, 0.75, 0.875)
        )
        @JvmStatic private val SHAPE_SOUTH = Shapes.or(
            Shapes.box(0.3125, -0.0625, 0.4375, 0.625, 0.875, 0.5625),
            Shapes.box(0.0, 0.25, 0.4375, 0.9375, 0.5625, 0.5625),
            Shapes.box(0.1875, 0.0, 0.4375, 0.75, 0.8125, 0.5625),
            Shapes.box(0.0625, 0.125, 0.4375, 0.875, 0.6875, 0.5625),
            Shapes.box(0.125, 0.0625, 0.4375, 0.8125, 0.75, 0.5625)
        )
        @JvmStatic private val SHAPE_WEST = Shapes.or(
            Shapes.box(0.4375, -0.0625, 0.3125, 0.5625, 0.875, 0.625),
            Shapes.box(0.4375, 0.25, 0.0, 0.5625, 0.5625, 0.9375),
            Shapes.box(0.4375, 0.0, 0.1875, 0.5625, 0.8125, 0.75),
            Shapes.box(0.4375, 0.125, 0.0625, 0.5625, 0.6875, 0.875),
            Shapes.box(0.4375, 0.0625, 0.125, 0.5625, 0.75, 0.8125)
        )
    }
    init {
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH))
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return this.defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, entity, stack)
        level.getBlockEntity(pos)?.let { be ->
            if (be is DreamCatcherBlockEntity) {
                if (stack.item is DreamCatcherItem) {
                    stack.orCreateTag.getCompound("colors")?.let { tag ->
                        be.colorLeft = getDye(tag.getString("left"))
                        be.colorMiddle = getDye(tag.getString("middle"))
                        be.colorRight = getDye(tag.getString("right"))
                    }
                }
            }
        }
    }

    //TODO: Drop correct dreamcatcher on broken, right now not really feasable without datagen bc its a hassle
}