package dev.mayaqq.estrogen.client.registry.trinkets;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import earth.terrarium.baubly.client.BaubleRenderer;
import earth.terrarium.baubly.client.BaublyClient;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ThighHighRenderer implements BaubleRenderer {

    public ThighHighRenderer() {}

    public static void register() {
        BaublyClient.registerBaubleRenderer(EstrogenItems.THIGH_HIGHS.get(), new ThighHighRenderer());
    }

    @Override
    public void render(ItemStack stack, SlotInfo slotContext, PoseStack matrixStack, EntityModel<? extends LivingEntity> entityModel, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}
