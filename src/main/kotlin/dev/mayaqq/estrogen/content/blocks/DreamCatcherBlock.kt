@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import com.mojang.serialization.MapCodec
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.blockEntities.DreamCatcherBlockEntity
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.utils.TriColor
import invoke.kitty.kritter.blockEntity.BlockWithEntity
import invoke.kitty.kritter.platform.common.BlockColorProvider
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.White
import invoke.kitty.kritter.utils.color.toColor
import invoke.kitty.kritter.utils.shapes.allHorizontalDirections
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
import kotlin.jvm.optionals.getOrNull


class DreamCatcherBlock(properties: Properties) : HorizontalDirectionalBlock(properties), BlockWithEntity<DreamCatcherBlockEntity>, EntityBlock {
    override fun codec(): MapCodec<out HorizontalDirectionalBlock?> = simpleCodec(::DreamCatcherBlock)

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

    override val blockEntityClass: Class<out DreamCatcherBlockEntity> = DreamCatcherBlockEntity::class.java
    override fun blockEntityType(): BlockEntityType<out DreamCatcherBlockEntity> = EstrogenBlockEntities.DreamCatcher.value!!

    fun triColor(getter: BlockAndTintGetter, pos: BlockPos): TriColor? {
        getter.getBlockEntity(pos, EstrogenBlockEntities.DreamCatcher.value!!).getOrNull()?.let {
            return it.triColor
        }
        return null
    }

    fun getColor(getter: BlockAndTintGetter, pos: BlockPos, tintIndex: Int): Color {
        return when (tintIndex) {
            1 -> triColor(getter, pos)?.left?: White
            2 -> triColor(getter, pos)?.middle?: White
            3 -> triColor(getter, pos)?.right?: White
            else -> White
        }
    }

    companion object : BlockColorProvider {

        private val SHAPES = Shapes.or(
            Shapes.box(0.3125, -0.0625, 0.4375, 0.625, 0.875, 0.5625),
            Shapes.box(0.0, 0.25, 0.4375, 0.9375, 0.5625, 0.5625),
            Shapes.box(0.1875, 0.0, 0.4375, 0.75, 0.8125, 0.5625),
            Shapes.box(0.0625, 0.125, 0.4375, 0.875, 0.6875, 0.5625),
            Shapes.box(0.125, 0.0625, 0.4375, 0.8125, 0.75, 0.5625)
        ).allHorizontalDirections()

        val COLORED: BooleanProperty = BooleanProperty.create("colored")
        override fun getColor(
            state: BlockState,
            view: BlockAndTintGetter?,
            pos: BlockPos?,
            tintIndex: Int
        ): Color {
            if (state.`is`(EstrogenBlocks.DreamCatcher.value!!) && view != null && pos != null) {
                val color = (state.block as DreamCatcherBlock).getColor(view, pos, tintIndex)
                return color
            }
            return White
        }
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

    override fun getCloneItemStack(level: LevelReader, pos: BlockPos, state: BlockState): ItemStack {
        val newStack = super.getCloneItemStack(level, pos, state)
        level.getBlockEntity(pos)?.let { e ->
            val be = e as DreamCatcherBlockEntity
            be.triColor?.let { triColor ->
                val item = newStack.item as DreamCatcherItem
                item.setDyes(newStack, triColor)
            }
        }
        return newStack
    }
}