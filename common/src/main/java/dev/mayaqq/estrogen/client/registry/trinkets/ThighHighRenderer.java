package dev.mayaqq.estrogen.client.registry.trinkets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.CreateClient;
import com.simibubi.create.foundation.render.BakedModelRenderHelper;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.items.ThighHighsItem;
import earth.terrarium.baubly.client.BaubleRenderer;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public class ThighHighRenderer implements BaubleRenderer {

    public ThighHighRenderer() {}

    @Override
    public void render(ItemStack stack, SlotInfo slotContext, PoseStack matrixStack, EntityModel<? extends LivingEntity> entityModel, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if(!(entityModel instanceof HumanoidModel<? extends LivingEntity> human)) return;

        ThighHighsItem thighHighsItem = EstrogenItems.THIGH_HIGHS.get();
        Optional<ResourceLocation> customStyle = thighHighsItem.getStyle(stack);
        VertexConsumer buffer = renderTypeBuffer.getBuffer(RenderType.armorCutoutNoCull(InventoryMenu.BLOCK_ATLAS));

        if(customStyle.isPresent()) {
            // TODO: (prob future release) option to have a model per leg
            SuperByteBuffer customThighHigh = customThighHighModel(customStyle.get());

            renderThighHigh(buffer, matrixStack, customThighHigh, human.leftLeg, -1, light);
            renderThighHigh(buffer, matrixStack, customThighHigh, human.rightLeg, -1, light);
        } else {
            SuperByteBuffer thighHigh = CachedBufferer.partial(EstrogenRenderer.THIGH_HIGH, Blocks.AIR.defaultBlockState());
            SuperByteBuffer thighHighOverlay = CachedBufferer.partial(EstrogenRenderer.THIGH_HIGH_OVERLAY, Blocks.AIR.defaultBlockState());

            int color1 = thighHighsItem.getColor(stack, 0);
            int color2 = thighHighsItem.getColor(stack, 1);

            renderThighHigh(buffer, matrixStack, thighHigh, human.leftLeg, color1, light);
            renderThighHigh(buffer, matrixStack, thighHighOverlay, human.leftLeg, color2, light);
            renderThighHigh(buffer, matrixStack, thighHigh, human.rightLeg, color1, light);
            renderThighHigh(buffer, matrixStack, thighHighOverlay, human.rightLeg, color2, light);
        }
    }

    private void renderThighHigh(VertexConsumer consumer, PoseStack ms, SuperByteBuffer model, ModelPart part, int color, int light) {
        part.translateAndRotate(model.getTransforms());
        model.multiply(Axis.ZP.rotationDegrees(180f));
        model.translate(-.5f, -.75f, -.5f);
        model.forEntityRender().light(light).color(color).renderInto(ms, consumer);
    }

    private SuperByteBuffer customThighHighModel(ResourceLocation style) {
        return CreateClient.BUFFER_CACHE.get(EstrogenRenderer.GENERIC, style, () -> {
            ResourceLocation modelLocation = new ResourceLocation(style.getNamespace(), "thigh_highs/" + style.getPath());
            ModelManager models = Minecraft.getInstance().getModelManager();
            BakedModel model = models.getModel(modelLocation);

            if(model == null) {
                Estrogen.LOGGER.warn("Missing thigh high model for style {}", style);
                return BakedModelRenderHelper.standardModelRender(models.getMissingModel(), Blocks.AIR.defaultBlockState());
            }
            return BakedModelRenderHelper.standardModelRender(model, Blocks.AIR.defaultBlockState());
        });
    }

}
