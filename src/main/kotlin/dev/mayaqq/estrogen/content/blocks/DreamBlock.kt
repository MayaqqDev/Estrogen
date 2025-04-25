@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.client.features.dash.ClientDash.refresh
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.features.dash.CommonDash
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import kotlin.reflect.KClass

class DreamBlock(p0: Properties) : Block(p0), BlockEntityBlock<DreamBlockEntity> {

    companion object {

        var lookAngle: Vec3? = null

        @JvmField val UP: BooleanProperty = BooleanProperty.create("up")
        @JvmField val DOWN: BooleanProperty = BooleanProperty.create("down")
        @JvmField val NORTH: BooleanProperty = BooleanProperty.create("north")
        @JvmField val SOUTH: BooleanProperty = BooleanProperty.create("south")
        @JvmField val EAST: BooleanProperty = BooleanProperty.create("east")
        @JvmField val WEST: BooleanProperty = BooleanProperty.create("west")

        fun directionProperty(direction: Direction): BooleanProperty {
            return when (direction) {
                Direction.UP -> UP
                Direction.DOWN -> DOWN
                Direction.NORTH -> NORTH
                Direction.SOUTH -> SOUTH
                Direction.EAST -> EAST
                Direction.WEST -> WEST
            }
        }
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

    override fun canBeReplaced(state: BlockState, fluid: Fluid): Boolean {
        return false
    }

    override fun getCollisionShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        if (context is EntityCollisionContext) {
            val entity = context.entity
            if (entity is Player && (CommonDash.isDashing(entity.getUUID()) || isInDreamBlock(entity))) {
                return Shapes.empty()
            }
        }
        return Shapes.block()
    }

    /**
     * Checks for if the player is colliding with a dream block.
     */
    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        val be = level.getBlockEntity(pos)
        if (be is DreamBlockEntity && level.isClientSide) {
            TODO("be.updateTexture(direction.axis !== Direction.Axis.Y)")
        }

        if (neighborState.`is`(this)) {
            return state.setValue<Boolean, Boolean>(
                directionProperty(
                    direction
                ), true
            )
        } else {
            return state.setValue<Boolean, Boolean>(
                directionProperty(
                    direction
                ), false
            )
        }
    }

    fun isInDreamBlock(player: Player): Boolean {
        if (player.isSpectator) return false

        val playerAABB = player.boundingBox
        val minPos = BlockPos.containing(playerAABB.minX, playerAABB.minY, playerAABB.minZ)
        val maxPos = BlockPos(
            Mth.ceil(playerAABB.maxX) - 1,
            Mth.ceil(playerAABB.maxY) - 1,
            Mth.ceil(playerAABB.maxZ) - 1
        )
        return BlockPos.betweenClosedStream(minPos, maxPos).anyMatch { pos: BlockPos ->
            player.level().getBlockState(pos).block is DreamBlock
        }

        // can't use betweenClosedStream because it also sometimes includes blocks that the player
        // is touching the face of, but not colliding with. >:(
        //return BlockPos.betweenClosedStream(playerAABB).anyMatch(
        //        pos -> player.level().getBlockState(pos).getBlock() instanceof DreamBlock
        //);
    }

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        entity.resetFallDistance()
        if (entity is Player && level.isClientSide) {
            refresh(entity)
            if (lookAngle == null) {
                lookAngle = entity.lookAngle
            }

            // if player hits a wall while inside dream blocks, make them bounce
            // Vec3 movement = player.getDeltaMovement();
            // if (movement.x() == 0 && lookAngle.x() != 0) lookAngle = lookAngle.multiply(-1, 1, 1);
            // if (movement.y() == 0 && lookAngle.y() != 0) lookAngle = lookAngle.multiply(1, -1, 1);
            // if (movement.z() == 0 && lookAngle.z() != 0) lookAngle = lookAngle.multiply(1, 1, -1);
            entity.deltaMovement = lookAngle!!.scale(2.0)
        }
    }
}