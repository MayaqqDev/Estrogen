package dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.mayaqq.cynosure.client.utils.lastPose
import dev.mayaqq.cynosure.utils.toVector3f
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import dev.mayaqq.estrogen.client.features.dash.DreamBlockEffect
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.collections.listOf

class DreamBlockRenderer(val ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<DreamBlockEntity> {

    override fun render(blockEntity: DreamBlockEntity, partialTick: Float, poseStack: PoseStack, buffer: MultiBufferSource, packedLight: Int, packedOverlay: Int) {
        if (blockEntity.shouldRender()) {
            DynamicDreamTexture.prepare()
            for (direction in Direction.entries) {
                if (blockEntity.isTouchingDreamBlock(direction)) continue
                renderFaceShader(blockEntity, poseStack.lastPose, buffer.getBuffer(EstrogenRenderTypes.DREAM_BLOCK), direction)
            }
        }
    }

    private fun renderFaceShader(blockEntity: DreamBlockEntity, pose: Matrix4f, consumer: VertexConsumer, direction: Direction) {
        val vertices = faceVertices[direction]!!
        val directions = vertexDirections[direction]!!

        // vertices for outer face
        for ((i, vertex) in vertices.asReversed().withIndex()) {
            val vertexCoords = Vector3f(vertex)
                .mul(0.999f)
                .add(0.0005f, 0.0005f, 0.0005f)
            val uv = vertexUVs[i]
            addVertexShader(pose, consumer, vertexCoords, true, uv)
        }
        // vertices for inner face
        for ((i, vertex) in vertices.withIndex()) {
            val adjacentDirections = directions[i] to directions[(i + 1) % 4]
            val vertexCoords2D = positionInnerVertex(blockEntity, direction, adjacentDirections)
            val vertexCoords3D = Vector3f(vertex)
                .sub(
                    direction
                        .normal
                        .toVector3f()
                        .mul(1f/16f)
                ).add(
                    adjacentDirections.first
                        .normal
                        .toVector3f()
                        .mul(vertexCoords2D.first.toFloat() / 16f)
                ).add(
                    adjacentDirections.second
                        .normal
                        .toVector3f()
                        .mul(vertexCoords2D.second.toFloat() / 16f)
                )
            addVertexShader(pose, consumer, vertexCoords3D, false)
        }
    }

    private fun positionInnerVertex(
        blockEntity: DreamBlockEntity,
        faceDirection: Direction,
        vertexDirections: Pair<Direction, Direction>
    ): Pair<Int, Int> {
        val isTouching = blockEntity.isTouchingDreamBlock(vertexDirections.first) to
                blockEntity.isTouchingDreamBlock(vertexDirections.second)
        return when (isTouching) {
            false to false -> -1 to -1
            false to true -> -1 to 1
            true to false -> 1 to -1
            true to true -> positionVertexCheckCorners(
                blockEntity, faceDirection, vertexDirections
            )
            else -> -1 to -1
        }
    }

    private fun positionVertexCheckCorners(
        blockEntity: DreamBlockEntity,
        faceDirection: Direction,
        vertexDirections: Pair<Direction, Direction>
    ): Pair<Int, Int> {
        val isTouching = blockEntity.isTouchingTouchingDreamBlock(vertexDirections.first, faceDirection) to
                blockEntity.isTouchingTouchingDreamBlock(vertexDirections.second, faceDirection)
        return when (isTouching) {
            false to false -> 0 to 0
            false to true -> 0 to 1
            true to false -> 1 to 0
            true to true -> 1 to 1
            else -> 0 to 0
        }
    }

    private fun DreamBlockEntity.isTouchingTouchingDreamBlock(blockDirection: Direction, faceDirection: Direction): Boolean {
        return this.level?.let { level ->
            level.getBlockEntity(this.blockPos.relative(blockDirection))?.let { blockEntity ->
                if (blockEntity is DreamBlockEntity) {
                    blockEntity.isTouchingDreamBlock(faceDirection)
                } else false
            } ?: false
        } ?: false
    }

    private fun addVertexShader(
        pose: Matrix4f,
        consumer: VertexConsumer,
        position: Vector3f,
        isBorder: Boolean,
        uv: Pair<Int, Int> = 0 to 0
    ) {
        val borderChannel = if (isBorder) 255 else 0
        val seeThroughChannel = if (DreamBlockEffect.isInDreamBlock) 255 else 0
        consumer.vertex(pose, position.x, position.y, position.z)
            .color(borderChannel, seeThroughChannel, 0, 0)
            .uv(uv.first.toFloat(), uv.second.toFloat())
            .uv2(LightTexture.FULL_BRIGHT)
            .normal(0f, 0f, 0f)
            .endVertex()
    }

    override fun getViewDistance(): Int {
        return 256
    }

    companion object {
        fun DreamBlockEntity.shouldRender(): Boolean = isPersistent
                || Minecraft.getInstance().player?.hasEffect(EstrogenEffects.Dreaming) == true

        val vertexUVs = listOf(
            0 to 0,
            1 to 0,
            1 to 1,
            0 to 1
        )
        val faceVertices = mapOf(
            Direction.DOWN to listOf(Vector3f(0f, 0f, 0f), Vector3f(1f, 0f, 0f), Vector3f(1f, 0f, 1f), Vector3f(0f, 0f, 1f)),
            Direction.UP to listOf(Vector3f(0f, 1f, 0f), Vector3f(0f, 1f, 1f), Vector3f(1f, 1f, 1f), Vector3f(1f, 1f, 0f)),
            Direction.NORTH to listOf(Vector3f(0f, 0f, 0f), Vector3f(0f, 1f, 0f), Vector3f(1f, 1f, 0f), Vector3f(1f, 0f, 0f)),
            Direction.SOUTH to listOf(Vector3f(0f, 0f, 1f), Vector3f(1f, 0f, 1f), Vector3f(1f, 1f, 1f), Vector3f(0f, 1f, 1f)),
            Direction.WEST to listOf(Vector3f(0f, 0f, 0f), Vector3f(0f, 0f, 1f), Vector3f(0f, 1f, 1f), Vector3f(0f, 1f, 0f)),
            Direction.EAST to listOf(Vector3f(1f, 0f, 0f), Vector3f(1f, 1f, 0f), Vector3f(1f, 1f, 1f), Vector3f(1f, 0f, 1f)),
        )
        val vertexDirections = mapOf(
            Direction.DOWN to listOf(Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH),
            Direction.UP to listOf(Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST),
            Direction.NORTH to listOf(Direction.DOWN, Direction.WEST, Direction.UP, Direction.EAST),
            Direction.SOUTH to listOf(Direction.WEST, Direction.DOWN, Direction.EAST, Direction.UP),
            Direction.WEST to listOf(Direction.NORTH, Direction.DOWN, Direction.SOUTH, Direction.UP),
            Direction.EAST to listOf(Direction.DOWN, Direction.NORTH, Direction.UP, Direction.SOUTH)
        )
    }
}
