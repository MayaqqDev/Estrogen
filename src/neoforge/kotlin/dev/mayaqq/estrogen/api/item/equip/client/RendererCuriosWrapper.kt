package dev.mayaqq.estrogen.api.item.equip.client

import com.mojang.blaze3d.vertex.PoseStack
import dev.mayaqq.estrogen.api.item.equip.slotInfo
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.client.ICurioRenderer

data class RendererCuriosWrapper(val equipRenderer: EquipRenderer) : ICurioRenderer {
    override fun <T : LivingEntity, M : EntityModel<T>> render(
        stack: ItemStack,
        slotContext: SlotContext,
        matrixStack: PoseStack,
        renderLayerParent: RenderLayerParent<T, M>,
        renderTypeBuffer: MultiBufferSource,
        light: Int,
        limbSwing: Float,
        limbSwingAmount: Float,
        partialTicks: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        equipRenderer.render(
            stack,
            slotContext.slotInfo(),
            matrixStack,
            renderLayerParent.model,
            renderTypeBuffer,
            light,
            limbSwing,
            limbSwingAmount,
            partialTicks,
            ageInTicks,
            netHeadYaw,
            headPitch
        )
    }
}