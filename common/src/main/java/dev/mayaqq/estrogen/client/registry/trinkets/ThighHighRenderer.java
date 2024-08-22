package dev.mayaqq.estrogen.client.registry.trinkets;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.render.BakedModelRenderHelper;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import earth.terrarium.baubly.client.BaubleRenderer;
import earth.terrarium.baubly.client.BaublyClient;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class ThighHighRenderer implements BaubleRenderer {

    PoseStack local = new PoseStack();

    public ThighHighRenderer() {}

    public static void register() {
        BaublyClient.registerBaubleRenderer(EstrogenItems.THIGH_HIGHS.get(), new ThighHighRenderer());
    }

    @Override
    public void render(ItemStack stack, SlotInfo slotContext, PoseStack matrixStack, EntityModel<? extends LivingEntity> entityModel, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if(!(entityModel instanceof HumanoidModel<? extends LivingEntity> human)) return;

        ThighHighsItem thighHighsItem = EstrogenItems.THIGH_HIGHS.get();
        ResourceLocation customStyle = thighHighsItem.getStyle(stack);
        VertexConsumer buffer = renderTypeBuffer.getBuffer(RenderType.armorCutoutNoCull(InventoryMenu.BLOCK_ATLAS));

        if(customStyle != null) {
            // TODO: (prob future release) option to have a model per leg
            SuperByteBuffer customThighHigh = customThighHighModel(customStyle);

            renderThighHigh(buffer, matrixStack, customThighHigh, human.leftLeg, -1);
            renderThighHigh(buffer, matrixStack, customThighHigh, human.rightLeg, -1);
        } else {
            SuperByteBuffer thighHigh = CachedBufferer.partial(EstrogenRenderer.THIGH_HIGH, Blocks.AIR.defaultBlockState());
            SuperByteBuffer thighHighOverlay = CachedBufferer.partial(EstrogenRenderer.THIGH_HIGH_OVERLAY, Blocks.AIR.defaultBlockState());

            int color1 = thighHighsItem.getColor(stack, 0);
            int color2 = thighHighsItem.getColor(stack, 1);


            renderThighHigh(buffer, matrixStack, thighHigh, human.leftLeg, color1);
            renderThighHigh(buffer, matrixStack, thighHighOverlay, human.leftLeg, color2);
            renderThighHigh(buffer, matrixStack, thighHigh, human.rightLeg, color1);
            renderThighHigh(buffer, matrixStack, thighHighOverlay, human.rightLeg, color2);
        }
    }

    private void renderThighHigh(VertexConsumer consumer, PoseStack ms, SuperByteBuffer model, ModelPart part, int color) {
        local.pushPose();
        part.translateAndRotate(local);
        TransformStack.cast(local)
            .rotate(Direction.SOUTH, 180f * Mth.DEG_TO_RAD)
            .translate(-.5f, -.75f, -.5f);
        model.forEntityRender().transform(local).color(color).renderInto(ms, consumer);
        local.popPose();
    }

    private SuperByteBuffer customThighHighModel(ResourceLocation style) {
        return CreateClient.BUFFER_CACHE.get(EstrogenRenderer.GENERIC, style, () -> {
                ResourceLocation modelLocation = new ResourceLocation(style.getNamespace(), "thigh_high_styles/" + style.getPath());
                BakedModel model = Minecraft.getInstance().getModelManager().getModel(modelLocation);
                return BakedModelRenderHelper.standardModelRender(model, Blocks.AIR.defaultBlockState());
            });
    }

}
