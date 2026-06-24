package dev.mayaqq.estrogen.client.content.baubles

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import dev.engine_room.flywheel.lib.util.RendererReloadCache
import dev.mayaqq.cynosure.client.models.baked.Mesh
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.White
import dev.mayaqq.cynosure.utils.colors.withAlpha
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.utils.render.buildMesh
import dev.mayaqq.estrogen.utils.render.getModel
import dev.mayaqq.estrogen.utils.render.mesh
import dev.mayaqq.estrogen.utils.render.render
import earth.terrarium.baubly.client.BaubleRenderer
import earth.terrarium.baubly.common.SlotInfo
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.item.ItemStack

class ThighHighsRenderer : BaubleRenderer {

    companion object {
        private val STYLE_MESH_CACHE: RendererReloadCache<ResourceLocation, Mesh> = RendererReloadCache { id ->
            val modelId = ResourceLocation(id.namespace, "thigh_highs/${id.path}")
            McClient.modelManager.getModel(modelId).buildMesh()
        }
    }

    override fun render(
        stack: ItemStack,
        slotInfo: SlotInfo,
        matrices: PoseStack,
        model: EntityModel<out LivingEntity>,
        bufferSource: MultiBufferSource,
        light: Int,
        p6: Float,
        p7: Float,
        p8: Float,
        p9: Float,
        p10: Float,
        p11: Float
    ) {
        if (model !is HumanoidModel) return
        val style = EstrogenItems.ThighHighs.getStyle(stack)
        val buffer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(InventoryMenu.BLOCK_ATLAS))

        if (style != null) {
            val mesh = STYLE_MESH_CACHE[style]
            renderThighHigh(buffer, matrices, mesh, model.leftLeg, White, light)
            renderThighHigh(buffer, matrices, mesh, model.rightLeg, White, light)
        } else {
            val baseMesh = EstrogenRenderer.THIGH_HIGH.mesh
            val baseColor = Color(EstrogenItems.ThighHighs.getColor(stack, 0)) withAlpha 255
            renderThighHigh(buffer, matrices, baseMesh, model.leftLeg, baseColor, light)
            renderThighHigh(buffer, matrices, baseMesh, model.rightLeg, baseColor, light)

            val overlayMesh = EstrogenRenderer.THIGH_HIGH_OVERLAY.mesh
            val overlayColor = Color(EstrogenItems.ThighHighs.getColor(stack, 1)) withAlpha 255
            renderThighHigh(buffer, matrices, overlayMesh, model.leftLeg, overlayColor, light)
            renderThighHigh(buffer, matrices, overlayMesh, model.rightLeg, overlayColor, light)
        }
    }

    private fun renderThighHigh(consumer: VertexConsumer, matrices: PoseStack, mesh: Mesh, part: ModelPart, color: Color, light: Int) {
        matrices.pushPop {
            part.translateAndRotate(matrices)
            mulPose(Axis.ZP.rotationDegrees(180f))
            translate(-.5f, -.75f, -.5f)
            mesh.render(consumer, matrices, color, light, OverlayTexture.NO_OVERLAY)
        }
    }
}