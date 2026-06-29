package dev.mayaqq.estrogen.api.item.equip.client

import com.mojang.blaze3d.vertex.PoseStack
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.client.TrinketRenderer
import dev.mayaqq.estrogen.api.item.equip.slotInfo
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

data class RendererTrinketWrapper(val equipRenderer: EquipRenderer) : TrinketRenderer {
    override fun render(
        itemStack: ItemStack,
        slotReference: SlotReference,
        entityModel: EntityModel<out LivingEntity>,
        poseStack: PoseStack,
        multiBufferSource: MultiBufferSource,
        light: Int,
        livingEntity: LivingEntity,
        limbAngle: Float,
        limbDistance: Float,
        tickDelta: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        equipRenderer.render(
            itemStack,
            slotReference.slotInfo(livingEntity),
            poseStack,
            entityModel,
            multiBufferSource,
            light,
            limbAngle,
            limbDistance,
            tickDelta,
            animationProgress,
            headYaw,
            headPitch
        )
    }
}