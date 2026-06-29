package dev.mayaqq.estrogen.api.item.equip.client

import com.mojang.blaze3d.vertex.PoseStack
import dev.mayaqq.estrogen.api.item.equip.SlotInfo
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

interface EquipRenderer {
    fun render(
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
    )
}