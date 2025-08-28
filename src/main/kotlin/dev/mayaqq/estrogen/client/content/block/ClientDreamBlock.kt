package dev.mayaqq.estrogen.client.content.block

import dev.mayaqq.cynosure.core.Environment
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.world.LevelEvent
import dev.mayaqq.estrogen.client.content.models.ConnectionState
import dev.mayaqq.estrogen.client.features.TextRendererFeatures
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.content.blocks.DreamBlock.Companion.isTouchingDreamBlock
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.mixin.client.accessor.LevelRendererAccessor
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.Material
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.SectionPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.block.state.BlockState

@EventSubscriber(env = [Environment.CLIENT])
object ClientDreamBlock {

    val DORMANT_MODEL: ResourceLocation = id("block/dream_block/dormant_dream_block")

    val DORMANT_CONNECTED_TEXTURE: Material = Material(InventoryMenu.BLOCK_ATLAS, id("block/dormant_dream_block/dormant_dream_block_connected"))

    private var rebuildDreamChunks = false

    @Subscription
    fun tick(event: LevelEvent.EndTick) {

        if (TextRendererFeatures.obfuscate != Minecraft.getInstance().player?.hasEffect(EstrogenEffects.Dreaming)) {
            rebuildDreamChunks = true
        }

        if (event.isClientSide && rebuildDreamChunks) {
            rebuildDreamChunks = false

            val iter = DreamBlockEntity.CHUNKS.longIterator()
            while (iter.hasNext()) {
                val pos = iter.nextLong()
                (Minecraft.getInstance().levelRenderer as LevelRendererAccessor)
                    .invokeSetSetionDirty(SectionPos.x(pos), SectionPos.y(pos), SectionPos.z(pos))
            }
        }
    }

    fun getConnectionForFace(view: BlockAndTintGetter, pos: BlockPos, state: BlockState, face: Direction): ConnectionState {
        val positive = face.axisDirection == Direction.AxisDirection.POSITIVE
        var upDir = face.upAdjusted
        var rightDir = face.rightAdjusted
        if (positive) rightDir = rightDir.opposite
        if (face == Direction.DOWN) {
            upDir = upDir.opposite
            rightDir = rightDir.opposite
        }

        val upState = view.getBlockState(pos.relative(upDir))
        val downState = view.getBlockState(pos.relative(upDir.opposite))
        val rightState = view.getBlockState(pos.relative(rightDir))
        val leftState = view.getBlockState(pos.relative(rightDir.opposite))

        return ConnectionState(
            up = isTouchingDreamBlock(state, upDir),
            down = isTouchingDreamBlock(state, upDir.opposite),
            right = isTouchingDreamBlock(state, rightDir),
            left = isTouchingDreamBlock(state, rightDir.opposite),
            topRight = isTouchingDreamBlock(upState, rightDir) || isTouchingDreamBlock(rightState, upDir),
            topLeft = isTouchingDreamBlock(upState, rightDir.opposite) || isTouchingDreamBlock(leftState, upDir),
            bottomRight = isTouchingDreamBlock(downState, rightDir) || isTouchingDreamBlock(rightState, upDir.opposite),
            bottomLeft = isTouchingDreamBlock(downState, rightDir.opposite) || isTouchingDreamBlock(leftState, upDir.opposite),
        )
    }

    private val Direction.upAdjusted: Direction get() = if (axis.isHorizontal) Direction.UP else Direction.EAST

    private val Direction.rightAdjusted: Direction get() = if (axis === Direction.Axis.X) Direction.SOUTH else
        if (axis.isHorizontal) Direction.WEST else Direction.NORTH
}