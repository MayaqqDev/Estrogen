package dev.mayaqq.estrogen.client.content.baubles

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import dev.engine_room.flywheel.lib.util.RendererReloadCache
import dev.mayaqq.cynosure.client.models.baked.Mesh
import dev.mayaqq.cynosure.client.utils.pushPop
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.helpers.McClient
import dev.mayaqq.estrogen.api.item.equip.SlotInfo
import dev.mayaqq.estrogen.api.item.equip.client.EquipRenderer
import dev.mayaqq.estrogen.client.content.EstrogenRenderer
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.utils.render.buildMesh
import dev.mayaqq.estrogen.utils.render.mesh
import dev.mayaqq.estrogen.utils.render.render
import invoke.kitty.kritter.client.model.getModel
import invoke.kitty.kritter.platform.identifierOf
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.White
import invoke.kitty.kritter.utils.color.toColor
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.item.ItemStack


class ThighHighsRenderer : EquipRenderer {

    companion object {
        private val STYLE_MESH_CACHE: RendererReloadCache<ResourceLocation, Mesh> = RendererReloadCache { id ->
            val modelId = identifierOf(id.namespace, "thigh_highs/${id.path}")
            (McClient.modelManager.getModel(modelId) ?: McClient.modelManager.missingModel).buildMesh()
        }
    }

    override fun render(
        stack: ItemStack,
        slot: SlotInfo,
        poseStack: PoseStack,
        model: EntityModel<out LivingEntity>,
        buffer: MultiBufferSource,
        light: Int,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        if (model !is HumanoidModel) return
        val style = EstrogenItems.ThighHighs.value!!.getStyle(stack)
        val buffer = buffer.getBuffer(RenderType.armorCutoutNoCull(InventoryMenu.BLOCK_ATLAS))

        if (style != null) {
            val mesh = STYLE_MESH_CACHE[style]
            renderThighHigh(buffer, poseStack, mesh, model.leftLeg, White, light)
            renderThighHigh(buffer, poseStack, mesh, model.rightLeg, White, light)
        } else {
            val baseMesh = EstrogenRenderer.THIGH_HIGH.mesh
            val baseColor = (EstrogenItems.ThighHighs.value!!.getColor(stack, 0)) alpha 255
            renderThighHigh(buffer, poseStack, baseMesh, model.leftLeg, baseColor, light)
            renderThighHigh(buffer, poseStack, baseMesh, model.rightLeg, baseColor, light)

            val overlayMesh = EstrogenRenderer.THIGH_HIGH_OVERLAY.mesh
            val overlayColor = (EstrogenItems.ThighHighs.value!!.getColor(stack, 1)) alpha 255
            renderThighHigh(buffer, poseStack, overlayMesh, model.leftLeg, overlayColor, light)
            renderThighHigh(buffer, poseStack, overlayMesh, model.rightLeg, overlayColor, light)
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