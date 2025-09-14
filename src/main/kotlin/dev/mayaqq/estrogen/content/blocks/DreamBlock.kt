@file:Suppress("OVERRIDE_DEPRECATION")

package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.cynosure.blocks.poi.anyInRange
import dev.mayaqq.cynosure.core.Environment
import dev.mayaqq.cynosure.core.currentEnvironment
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.PlayerConnectionEvent
import dev.mayaqq.cynosure.events.entity.player.interaction.InteractionEvent
import dev.mayaqq.cynosure.utils.of
import dev.mayaqq.estrogen.client.features.TextRendererFeatures
import dev.mayaqq.estrogen.client.features.dash.ClientDash.refresh
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.features.dash.CommonDash
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.c2s.DreamBlockRipplePacket
import dev.mayaqq.estrogen.network.messages.s2c.DreamBlockSeedPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.AbstractGlassBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.levelgen.WorldOptions
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.EntityCollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.apache.commons.codec.digest.MessageDigestAlgorithms
import uwu.serenity.kritter.client.stdlib.clientOnly
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import java.security.MessageDigest
import kotlin.reflect.KClass

class DreamBlock(p0: Properties) : AbstractGlassBlock(p0), BlockEntityBlock<DreamBlockEntity> {

    init {
        registerDefaultState(defaultBlockState()
            .setValue(UP, false)
            .setValue(DOWN, false)
            .setValue(EAST, false)
            .setValue(WEST, false)
            .setValue(NORTH, false)
            .setValue(SOUTH, false)
            .setValue(PERSISTENT, false))
    }

    override val blockEntityClass: KClass<out DreamBlockEntity> = DreamBlockEntity::class

    override fun getBlockEntityType(): BlockEntityType<out DreamBlockEntity> = EstrogenBlockEntities.DreamBlock

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(PERSISTENT, UP, DOWN, EAST, WEST, NORTH, SOUTH)
    }

    override fun getSoundType(state: BlockState): SoundType {
        clientOnly {
            if(TextRendererFeatures.obfuscate) return EstrogenSoundTypes.DREAM_BLOCK_ACTIVE
        }
        return if (state.getValue(PERSISTENT)) EstrogenSoundTypes.DREAM_BLOCK_ACTIVE else EstrogenSoundTypes.DREAM_BLOCK_DORMANT
    }

    override fun canBeReplaced(state: BlockState, fluid: Fluid): Boolean {
        return false
    }

    override fun getRenderShape(p0: BlockState): RenderShape =
        if ((currentEnvironment == Environment.CLIENT && TextRendererFeatures.obfuscate) || p0.getValue(PERSISTENT)) RenderShape.ENTITYBLOCK_ANIMATED else RenderShape.MODEL

    override fun getCollisionShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        if (context is EntityCollisionContext) {
            val entity = context.entity
            if (
                entity is Player &&
                canEntityUse(state, entity) &&
                (CommonDash.isDashing(entity.getUUID()) || isInDreamBlock(entity))
                ) {
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
        return if (neighborState of this && neighborState.getValue(PERSISTENT) == state.getValue(PERSISTENT))
            state.setValue(directionProperty(direction), true)
        else state.setValue(directionProperty(direction), false)
    }

    override fun isRandomlyTicking(state: BlockState): Boolean = !state.getValue(PERSISTENT)

    override fun randomTick(state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource) {
        if (state.getValue(PERSISTENT)) return

        EstrogenServerConfig.DreamBlock.dreamingTickChance
            .also { if (it < 100 && random.nextIntBetweenInclusive(0, 100) > it) return }

        val range = EstrogenServerConfig.DreamBlock.dreamingEffectRange
        val aabb = AABB(
            (-range).toDouble(), (-range).toDouble(), (-range).toDouble(),
            range.toDouble(), range.toDouble(), range.toDouble()
        ).move(pos)

        val entities = level.getPlayers {
            it.isSleeping
                    && aabb.contains(it.position())
                    && it.hasEffect(EstrogenEffects.Estrogen)
                    && !it.hasEffect(EstrogenEffects.Dreaming)
        }

        if (entities.isEmpty()) return
        val player = entities[random.nextInt(0, entities.size)]

        if (level.poiManager.anyInRange(
                EstrogenPoiTypes.DreamCatcher,
                player.blockPosition(),
                EstrogenServerConfig.DreamBlock.dreamCatcherRange
        )) return

        player.stopSleeping()
        player.addEffect(MobEffectInstance(
            EstrogenEffects.Dreaming,
            MobEffectInstance.INFINITE_DURATION,
            0, true, false
        ))
    }

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (!canEntityUse(state, entity as? LivingEntity)) return
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

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean = false

    override fun getLightBlock(state: BlockState, level: BlockGetter, pos: BlockPos): Int {
        clientOnly {
            if (level is ClientLevel)
                return if (canEntityUse(state, Minecraft.getInstance().player)) level.maxLightLevel else 0
        }
        return if (state.getValue(PERSISTENT)) level.maxLightLevel else 0
    }

    @EventSubscriber
    companion object {

        @JvmField val PERSISTENT: BooleanProperty = BooleanProperty.create("persistent")
        @JvmField val UP: BooleanProperty = BooleanProperty.create("up")
        @JvmField val DOWN: BooleanProperty = BooleanProperty.create("down")
        @JvmField val NORTH: BooleanProperty = BooleanProperty.create("north")
        @JvmField val SOUTH: BooleanProperty = BooleanProperty.create("south")
        @JvmField val EAST: BooleanProperty = BooleanProperty.create("east")
        @JvmField val WEST: BooleanProperty = BooleanProperty.create("west")

        var lookAngle: Vec3? = null
        private val md5 = MessageDigest.getInstance(MessageDigestAlgorithms.MD5)

        fun directionProperty(direction: Direction): BooleanProperty = when (direction) {
            Direction.DOWN -> DOWN
            Direction.UP -> UP
            Direction.NORTH -> NORTH
            Direction.SOUTH -> SOUTH
            Direction.WEST -> WEST
            Direction.EAST -> EAST
        }

        @JvmStatic
        fun canEntityUse(state: BlockState, entity: LivingEntity?): Boolean =
            state.getValue(PERSISTENT) || entity?.hasEffect(EstrogenEffects.Dreaming) == true

        @JvmStatic
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
                player.level().getBlockState(pos).let { it.block is DreamBlock && canEntityUse(it, player) }
            }

            // can't use betweenClosedStream because it also sometimes includes blocks that the player
            // is touching the face of, but not colliding with. >:(
        }

        @JvmStatic
        fun isTouchingDreamBlock(state: BlockState, direction: Direction): Boolean =
            state.block is DreamBlock && state.getValue(directionProperty(direction))

        @Subscription
        internal fun onPlayerJoin(event: PlayerConnectionEvent.Join) {
            val seed = event.player.serverLevel().seed.toString()
            val bytes = md5.digest(seed.toByteArray())
            val newSeed = WorldOptions.parseSeed(String(bytes)).asLong
            EstrogenNetwork.sendToPlayer(DreamBlockSeedPacket(newSeed), event.player)
        }

        @Subscription
        internal fun onAttackBlock(event: InteractionEvent.AttackBlock) {
            if (event.player.abilities.instabuild) return
            val state = event.level.getBlockState(event.pos)
            if (state.`is`(EstrogenBlocks.DreamBlock)) {
                clientOnly {
                    if (event.level.isClientSide && TextRendererFeatures.obfuscate || state.getValue(PERSISTENT)) {
                        event.result = InteractionResult.FAIL
                        val hitResult = Minecraft.getInstance().hitResult
                        if (hitResult is BlockHitResult) {
                            EstrogenNetwork.sendToServer(DreamBlockRipplePacket(hitResult.location, hitResult.direction))
                        }
                        return
                    }
                }

                if (state.getValue(PERSISTENT)) {
                    event.result = InteractionResult.FAIL
                    return
                }
            }
        }
    }
}