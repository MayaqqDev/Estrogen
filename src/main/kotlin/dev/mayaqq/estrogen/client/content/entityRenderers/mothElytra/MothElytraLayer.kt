package dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra

import com.mojang.blaze3d.vertex.PoseStack
import dev.mayaqq.cynosure.modId
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.EntityModelSet
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity

class MothElytraLayer<T : LivingEntity?, M : EntityModel<T>?>(
    renderer: RenderLayerParent<T, M>,
    modelSet: EntityModelSet
) : RenderLayer<T, M>(renderer) {
    private val elytraModel: MothElytraModel<T> = MothElytraModel(modelSet.bakeLayer(MothElytraModel.LAYER_LOCATION))

    override fun render(
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        livingEntity: T,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        val itemStack = livingEntity!!.getItemBySlot(EquipmentSlot.CHEST)
        if (!itemStack.`is`(EstrogenItems.MOTH_ELYTRA)) {
            return
        }
        poseStack.pushPose()
        val name = itemStack.displayName.string.replace("[", "").replace("]", "")
        if (name.equals("big", ignoreCase = true)) {
            poseStack.scale(2f, 2f, 2f)
        } else if (name.equals("small", ignoreCase = true)) {
            poseStack.scale(1f, 1f, 1f)
        } else {
            poseStack.scale(1.5f, 1.5f, 1.5f)
        }
        poseStack.translate(0.0f, 0.15f, -0.05f)
        this.parentModel?.copyPropertiesTo(this.elytraModel)
        elytraModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        val vertexConsumer = ItemRenderer.getArmorFoilBuffer(
            buffer, RenderType.armorCutoutNoCull(
                WINGS_LOCATION
            ), false, itemStack.hasFoil()
        )
        elytraModel.renderToBuffer(
            poseStack,
            vertexConsumer,
            packedLight,
            OverlayTexture.NO_OVERLAY,
            1.0f,
            1.0f,
            1.0f,
            1.0f
        )
        poseStack.popPose()
    }

    companion object {
        private val WINGS_LOCATION: ResourceLocation = modId("textures/entity/moth_elytra.png")
    }
}