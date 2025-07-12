package dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.client.content.blockRenderers.dreamBlock.texture.DynamicDreamTexture
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.blockEntities.DreamBlockEntity
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.core.Direction
import net.minecraft.world.entity.player.Player
import org.joml.Matrix4f

class DreamBlockRenderer(val ctx: BlockEntityRendererProvider.Context) : BlockEntityRenderer<DreamBlockEntity> {

    override fun render(p0: DreamBlockEntity, p1: Float, p2: PoseStack, p3: MultiBufferSource, p4: Int, p5: Int) {
        if (p0.shouldRender()) {
            DynamicDreamTexture.prepare()
            renderCubeShader(p0, p2.last().pose(), p3.getBuffer(EstrogenRenderTypes.DREAM_BLOCK))
        }
    }
    private fun renderCubeShader(blockEntity: DreamBlockEntity, pose: Matrix4f, consumer: VertexConsumer) {
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH)
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH)
        this.renderFaceShader(blockEntity, pose, consumer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST)
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST)
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN)
        this.renderFaceShader(blockEntity, pose, consumer, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP)
    }

    private fun renderFaceShader(
        blockEntity: DreamBlockEntity,
        pose: Matrix4f,
        consumer: VertexConsumer,
        x0: Float,
        x1: Float,
        y0: Float,
        y1: Float,
        z0: Float,
        z1: Float,
        z2: Float,
        z3: Float,
        direction: Direction
    ) {
        addInnerVertexShader(blockEntity, pose, consumer, x0, y0, z0)
        addInnerVertexShader(blockEntity, pose, consumer, x1, y0, z1)
        addInnerVertexShader(blockEntity, pose, consumer, x1, y1, z2)
        addInnerVertexShader(blockEntity, pose, consumer, x0, y1, z3)

        if (blockEntity.isTouchingDreamBlock(direction)) return
        addOuterVertexShader(blockEntity, pose, consumer, x0, y1, z3)
        addOuterVertexShader(blockEntity, pose, consumer, x1, y1, z2)
        addOuterVertexShader(blockEntity, pose, consumer, x1, y0, z1)
        addOuterVertexShader(blockEntity, pose, consumer, x0, y0, z0)
    }

    /**
     * Vertices for the inner faces, which will have the shader applied.
     * Vertices are moved when there are neighboring dream blocks, so that their interiors connect.
     */
    private fun addInnerVertexShader(
        blockEntity: DreamBlockEntity,
        pose: Matrix4f,
        consumer: VertexConsumer,
        x: Float,
        y: Float,
        z: Float
    ) {
        // ternary nightmare
        val x2 =
            if (blockEntity.isTouchingDreamBlock(if (x > 0.5) Direction.EAST else Direction.WEST)) x else x * 7f / 8f + 1f / 16f
        val y2 =
            if (blockEntity.isTouchingDreamBlock(if (y > 0.5) Direction.UP else Direction.DOWN)) y else y * 7f / 8f + 1f / 16f
        val z2 =
            if (blockEntity.isTouchingDreamBlock(if (z > 0.5) Direction.SOUTH else Direction.NORTH)) z else z * 7f / 8f + 1f / 16f

        addVertexShader(pose, consumer, x2, y2, z2, false)
    }

    /**
     * Workaround to changing canOcclude() via config
     */
    private fun addOuterVertexShader(
        blockEntity: DreamBlockEntity,
        pose: Matrix4f,
        consumer: VertexConsumer,
        x: Float,
        y: Float,
        z: Float
    ) {
        val x2 = x * 0.999f + 0.0005f
        val y2 = y * 0.999f + 0.0005f
        val z2 = z * 0.999f + 0.0005f

        addVertexShader(pose, consumer, x2, y2, z2, true)
    }

    private fun addVertexShader(
        pose: Matrix4f,
        consumer: VertexConsumer,
        x: Float,
        y: Float,
        z: Float,
        isBorder: Boolean
    ) {
        consumer.vertex(pose, x, y, z)
        val borderChannel = if (isBorder) 255 else 0
        val seeThroughChannel = if (shouldSeeThrough()) 255 else 0
        consumer.color(borderChannel, seeThroughChannel, 0, 0)
        consumer.uv(0f, 0f)
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

        fun shouldSeeThrough(): Boolean {
            val player = Minecraft.getInstance().player as? Player
            player?.let {return DreamBlock.isInDreamBlock(player)} ?: return false
        }
    }
}
