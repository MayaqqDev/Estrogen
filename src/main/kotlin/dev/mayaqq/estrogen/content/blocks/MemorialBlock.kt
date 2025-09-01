package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.helpers.get
import dev.mayaqq.cynosure.utils.file.GlobalStorage
import dev.mayaqq.cynosure.utils.of
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.screen.MemorialScreen
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.blockEntities.MemorialBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists
import kotlin.io.path.notExists
import kotlin.reflect.KClass

class MemorialBlock(properties: Properties): BaseEntityBlock(properties), BlockEntityBlock<MemorialBlockEntity> {

    init {
        this.registerDefaultState(this.defaultBlockState().setValue(PART, 1))
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        result: BlockHitResult
    ): InteractionResult = if (level.isClientSide) {
        if (file.notExists()) file.createParentDirectories().createFile()
        McClient.setScreen(MemorialScreen())
        InteractionResult.SUCCESS
    } else InteractionResult.CONSUME

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(PART)
    }

    /* Shape
     * 56
     * 34
     * 12
     * Additionally, 3 is the only one with the model and 1 is the one that gets "placed"
     */

    override fun getRenderShape(state: BlockState): RenderShape {
        return if (state.getValue(PART) == 3) RenderShape.MODEL else RenderShape.INVISIBLE
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, byPiston: Boolean) {
        super.onRemove(state, level, pos, newState, byPiston)
        if (!level.isClientSide) {
            Direction.entries.forEach { dir ->
                if (level[pos.relative(dir)] of EstrogenBlocks.Memorial) {
                    level.removeBlock(pos.relative(dir), true)
                }
            }
        }
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, movedByPiston: Boolean) {
        if (!level.isClientSide) {
            when (state.getValue(PART)) {
                1 -> {
                    level.setBlock(pos.relative(Direction.WEST), state.setValue(PART, 2), 3)
                    level.setBlock(pos.above(), state.setValue(PART, 3), 3)
                    level.setBlock(pos.above().above(), state.setValue(PART, 5), 3)
                    level.blockUpdated(pos.above(), Blocks.AIR)
                    state.updateNeighbourShapes(level, pos.above(), 3)

                }
                3 -> {
                    level.setBlock(pos.relative(Direction.WEST), state.setValue(PART, 4), 3)
                }
                5 -> {
                    level.setBlock(pos.relative(Direction.WEST), state.setValue(PART, 6), 3)
                }
            }
            level.blockUpdated(pos, Blocks.AIR)
            state.updateNeighbourShapes(level, pos, 3)
        }
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return when (state.getValue(PART)) {
            5 -> SHAPE_5
            6 -> SHAPE_6
            else -> SHAPE_BASE
        }
    }

    override val blockEntityClass: KClass<out MemorialBlockEntity> = MemorialBlockEntity::class
    override fun getBlockEntityType(): BlockEntityType<out MemorialBlockEntity> = EstrogenBlockEntities.Memorial

    companion object {
        val PART = IntegerProperty.create("part", 1, 6)

        val SHAPE_BASE = Shapes.box(0.0, 0.0, 0.1875, 1.0, 1.0, 0.8125)
        val SHAPE_5 = Shapes.or(
            Shapes.box(0.0, 0.0, 0.1875, 1.0, 0.6875, 0.8125),
            Shapes.box(0.0, 0.6875, 0.1875, 0.6875, 1.0, 0.8125)
        )
        val SHAPE_6 = Shapes.or(
            Shapes.box(0.0, 0.0, 0.1875, 1.0, 0.6875, 0.8125),
            Shapes.box(0.3125, 0.6875, 0.1875, 1.0, 1.0, 0.8125)
        )

        val file: Path = GlobalStorage.getData(MOD_ID).resolve("memorial")

    }
}