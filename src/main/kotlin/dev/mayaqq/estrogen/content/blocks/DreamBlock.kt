package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import kotlin.reflect.KClass

class DreamBlock(p0: Properties) : Block(p0), BlockEntityBlock<DreamBlockEntity> {

    companion object {
        @JvmField val UP: BooleanProperty = BooleanProperty.create("up")
        @JvmField val DOWN: BooleanProperty = BooleanProperty.create("down")
        @JvmField val NORTH: BooleanProperty = BooleanProperty.create("north")
        @JvmField val SOUTH: BooleanProperty = BooleanProperty.create("south")
        @JvmField val EAST: BooleanProperty = BooleanProperty.create("east")
        @JvmField val WEST: BooleanProperty = BooleanProperty.create("west")
    }

    init {
        registerDefaultState(defaultBlockState()
            .setValue(UP, false)
            .setValue(DOWN, false)
            .setValue(NORTH, false)
            .setValue(SOUTH, false)
            .setValue(EAST, false)
            .setValue(WEST, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST)
    }

    override val blockEntityClass: KClass<out DreamBlockEntity> = DreamBlockEntity::class

    override fun getBlockEntityType(): BlockEntityType<out DreamBlockEntity> = EstrogenBlockEntities.DREAM_BLOCK


}