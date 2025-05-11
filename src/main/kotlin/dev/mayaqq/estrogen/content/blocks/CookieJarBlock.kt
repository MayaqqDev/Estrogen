package dev.mayaqq.estrogen.content.blocks

import dev.mayaqq.estrogen.content.AdvancementTriggers
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.EstrogenSoundTypes
import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.content.blockEntities.CookieJarBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import uwu.serenity.kritter.stdlib.BlockEntityBlock
import kotlin.reflect.KClass

class CookieJarBlock(properties: Properties) : BaseEntityBlock(properties), BlockEntityBlock<CookieJarBlockEntity>, SimpleWaterloggedBlock {
    companion object {
        private val WATERLOGGED: BooleanProperty = BlockStateProperties.WATERLOGGED
        private val BOUNDING_BOX = listOf(
            // Cork
            Shapes.box(0.375, 0.625, 0.375, 0.625, 0.8125, 0.625),
            // Big lid shape
            Shapes.box(0.1875 + 0.0625, 0.6875, 0.1875 + 0.0625, 0.8125 - 0.0625, 0.75, 0.8125 - 0.0625),
            // first part
            Shapes.box(0.3125, 0.625, 0.3125, 0.6875, 0.6875, 0.6875),
            // Main square
            Shapes.box(0.1875, 0.0, 0.1875, 0.8125, 0.625, 0.8125)
        ).reduce(Shapes::or)
    }

    init {
        this.registerDefaultState(
            stateDefinition.any()
                .setValue(WATERLOGGED, false)
        )
    }

    override fun getRenderShape(state: BlockState): RenderShape {
        return RenderShape.MODEL
    }

    override fun getStateForPlacement(blockPlaceContext: BlockPlaceContext): BlockState? {
        val fluidState = blockPlaceContext.level.getFluidState(blockPlaceContext.clickedPos)
        return defaultBlockState().setValue(WATERLOGGED, fluidState.type === Fluids.WATER)
    }

    override fun use(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hit: BlockHitResult
    ): InteractionResult {
        val cookieJarBlockEntity = level.getBlockEntityOfType(pos) ?: return InteractionResult.FAIL
        if (level.isClientSide) {
            return InteractionResult.CONSUME
        }

        val handItem = player.getItemInHand(hand)

        if (!handItem.isEmpty) {
            val remainder: ItemStack = cookieJarBlockEntity.addItemStack(handItem)
            if (ItemStack.matches(handItem, remainder)) {
                // jar was full, couldn't add item to jar
                level.playSound(null, pos, EstrogenSounds.JAR_FULL, SoundSource.BLOCKS, 1.0f, 1.0f)
            } else {
                if (!player.isCreative) handItem.count = remainder.count

                player.awardStat(Stats.ITEM_USED[handItem.item])
                level.playSound(
                    null,
                    pos,
                    EstrogenSounds.JAR_INSERT,
                    SoundSource.BLOCKS,
                    1.0f,
                    0.7f + 0.5f * (cookieJarBlockEntity.count.toFloat() / 512)
                )
                if (level is ServerLevel) {
                    AdvancementTriggers.InsertJar.trigger(player as ServerPlayer)
                    level.sendParticles(
                        ParticleTypes.CRIT,
                        pos.x.toDouble() + 0.5,
                        pos.y.toDouble() + 1.2,
                        pos.z.toDouble() + 0.5,
                        7,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                    )
                }
            }
        } else {
            // take whole stack if crouching
            val jarItemStack: ItemStack =
                if (player.isShiftKeyDown) cookieJarBlockEntity.removeItemStack() else cookieJarBlockEntity.remove1Item()

            if (!jarItemStack.isEmpty) {
                // removing item from jar
                level.playSound(
                    null,
                    pos,
                    EstrogenSounds.JAR_INSERT,
                    SoundSource.BLOCKS,
                    1.0f,
                    0.7f + 0.5f * (cookieJarBlockEntity.count as Float / 512)
                )
                if (level is ServerLevel) {
                    player.inventory.placeItemBackInInventory(jarItemStack)
                }
            } else {
                // jar was empty, couldn't remove item from jar
            }
        }

        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos)
        return InteractionResult.SUCCESS
    }

    override fun isPathfindable(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        type: PathComputationType
    ): Boolean {
        return false
    }

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return BOUNDING_BOX
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(WATERLOGGED)
    }

    override fun onRemove(state: BlockState, level: Level, blockPos: BlockPos, newState: BlockState, bl: Boolean) {
        val be: CookieJarBlockEntity? = level.getBlockEntityOfType(blockPos)
        if (!state.`is`(newState.block) && be != null) {
            Containers.dropContents(level, blockPos, be)
        }
        super.onRemove(state, level, blockPos, newState, bl)
    }

    override fun getFluidState(blockState: BlockState): FluidState {
        return if (blockState.getValue(WATERLOGGED)) Fluids.WATER.getSource(false) else super.getFluidState(blockState)
    }

    override fun getSoundType(blockState: BlockState): SoundType {
        return EstrogenSoundTypes.COOKIE_JAR
    }

    override fun onProjectileHit(
        level: Level,
        blockState: BlockState,
        blockHitResult: BlockHitResult,
        projectile: Projectile
    ) {
        val blockPos = blockHitResult.blockPos
        if (!level.isClientSide && projectile.mayInteract(level, blockPos)) {
            level.destroyBlock(blockPos, true, projectile)
        }
    }

    override fun hasAnalogOutputSignal(state: BlockState): Boolean {
        return true
    }

    override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos): Int {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos))
    }

    override fun getVisualShape(
        state: BlockState,
        level: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return Shapes.empty()
    }

    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos): Float {
        return 1.0f
    }

    override fun useShapeForLightOcclusion(state: BlockState): Boolean {
        return true
    }

    override fun propagatesSkylightDown(state: BlockState, level: BlockGetter, pos: BlockPos): Boolean {
        return true
    }

    override val blockEntityClass: KClass<out CookieJarBlockEntity> = CookieJarBlockEntity::class

    override fun getBlockEntityType(): BlockEntityType<out CookieJarBlockEntity> = EstrogenBlockEntities.CookieJar


}