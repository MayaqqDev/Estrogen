@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.cynosure.utils.allHorizontalDirections
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.blockEntities.DreamCatcherBlockEntity
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.utils.TriColor
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.*
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.KClass


class DreamCatcherBlock(properties: Properties) : HorizontalDirectionalBlock(properties), BlockEntityBlock<DreamCatcherBlockEntity>, EntityBlock {

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return canSupportCenter(level, pos.relative(Direction.UP), Direction.DOWN)
    }

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPES[state.getValue(FACING)]!!
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

    fun triColor(getter: BlockAndTintGetter, pos: BlockPos): TriColor? {
        getter.getBlockEntity(pos, EstrogenBlockEntities.DreamCatcher).getOrNull()?.let {
            return it.triColor
        }
        return null
    }

    fun getColor(getter: BlockAndTintGetter, pos: BlockPos, tintIndex: Int): Int {
        return when (tintIndex) {
            1 -> triColor(getter, pos)?.left?.toInt()?: -1
            2 -> triColor(getter, pos)?.middle?.toInt()?: -1
            3 -> triColor(getter, pos)?.right?.toInt()?: -1
            else -> -1
        }
    }

    companion object {

        private val SHAPES = Shapes.or(
            Shapes.box(0.3125, -0.0625, 0.4375, 0.625, 0.875, 0.5625),
            Shapes.box(0.0, 0.25, 0.4375, 0.9375, 0.5625, 0.5625),
            Shapes.box(0.1875, 0.0, 0.4375, 0.75, 0.8125, 0.5625),
            Shapes.box(0.0625, 0.125, 0.4375, 0.875, 0.6875, 0.5625),
            Shapes.box(0.125, 0.0625, 0.4375, 0.8125, 0.75, 0.5625)
        ).allHorizontalDirections()

        fun getBlockColor(state: BlockState, view: BlockAndTintGetter?, pos: BlockPos?, tint: Int): Int {
            if (state.`is`(EstrogenBlocks.DreamCatcher) && view != null && pos != null) {
                return (state.block as DreamCatcherBlock).getColor(view, pos, tint)
            }
            return -1
        }

        val COLORED: BooleanProperty = BooleanProperty.create("colored")
    }
    init {
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(COLORED, false))
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        return this.defaultBlockState()
            .setValue(
            FACING,
            context.horizontalDirection.opposite
        )
            .setValue(
                COLORED,
                context.itemInHand.item is DreamCatcherItem &&
                        !(context.itemInHand.item as DreamCatcherItem).isBlank(context.itemInHand)
            )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
        builder.add(COLORED)
    }

    override fun setPlacedBy(level: Level, pos: BlockPos, state: BlockState, entity: LivingEntity?, stack: ItemStack) {
        super.setPlacedBy(level, pos, state, entity, stack)
        level.getBlockEntity(pos)?.let { be ->
            if (be is DreamCatcherBlockEntity) {
                if (stack.item is DreamCatcherItem) {
                    val item = stack.item as DreamCatcherItem
                    if (!item.isBlank(stack)) {
                        be.setColors(item.triColor(stack)!!)
                    }
                }
            }
        }
    }

    override fun getCloneItemStack(block: BlockGetter, pos: BlockPos, state: BlockState): ItemStack {
        val newStack = super.getCloneItemStack(block, pos, state)
        block.getBlockEntity(pos)?.let { e ->
            val be = e as DreamCatcherBlockEntity
            be.triColor?.let { triColor ->
                val item = newStack.item as DreamCatcherItem
                item.setDyes(newStack, triColor)
            }
        }
        return newStack
    }
}