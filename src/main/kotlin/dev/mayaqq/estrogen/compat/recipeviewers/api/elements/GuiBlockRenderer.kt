package dev.mayaqq.estrogen.compat.recipeviewers.api.elements

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.math.Axis
import dev.engine_room.flywheel.lib.model.baked.SinglePosVirtualBlockGetter
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.compat.recipeviewers.api.CRVDrawable
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f


class GuiBlockRenderer(
    val block: BlockState,
    val blockEntity: BlockEntity? = null,
    val x: Int = 0,
    val y: Int = 0,
    val z: Int = 0,
    val rotation: Vec3 = Vec3(22.5, 45.0, 0.0),
    val scale: Double = 20.0,
) : CRVDrawable {
    override fun draw(
        graphics: GuiGraphics,
        offsetX: Int,
        offsetY: Int,
        mouseX: Int,
        mouseY: Int,
        delta: Float
    ) {
        graphics.pushPop {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F)
            RenderSystem.enableDepthTest()
            RenderSystem.enableBlend()
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            Lighting.setupFor3DItems()

            val dispatcher = McClient.blockRenderer
            val buffer = graphics.bufferSource()
            val rotationOffset = Vec3(0.5, 0.5, 0.5)
            translate(x.toDouble(), y.toDouble(), z.toDouble())
            scale(scale.toFloat(), scale.toFloat(), scale.toFloat())
            mulPose(Matrix4f().scaling(1f, -1f, 1f))
            translate(rotationOffset.x, rotationOffset.y, rotationOffset.z)
            mulPose(Axis.ZP.rotationDegrees(rotation.z.toFloat()))
            mulPose(Axis.XP.rotationDegrees(rotation.x.toFloat()))
            mulPose(Axis.YP.rotationDegrees(rotation.y.toFloat()))
            translate(-rotationOffset.x, -rotationOffset.y, -rotationOffset.z)

            RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

            val level = SinglePosVirtualBlockGetter.createFullBright()
            level.blockState(block)
            level.blockEntity(blockEntity)
            dispatcher.renderBatched(block, BlockPos.ZERO, level, this, buffer.getBuffer(RenderType.translucent()), true, RandomSource.create())
            buffer.endBatch()

            Lighting.setupFor3DItems()
        }
    }
}