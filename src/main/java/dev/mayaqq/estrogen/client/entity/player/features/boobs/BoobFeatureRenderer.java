package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.utils.Time;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;

import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.utils.Boob.boobSize;

@Environment(EnvType.CLIENT)
public class BoobFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public BoobFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {
        if (abstractClientPlayerEntity.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT) && AutoConfig.getConfigHolder(EstrogenConfig.class).getConfig().boobies && abstractClientPlayerEntity.hasSkinTexture() && !abstractClientPlayerEntity.isInvisible()) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getSkinTexture()));
            int m = LivingEntityRenderer.getOverlay(abstractClientPlayerEntity, 0.0F);
            matrixStack.push();
            float size;
            double startTime = abstractClientPlayerEntity.getAttributeBaseValue(BOOB_GROWING_START_TIME);
            if (startTime >= 0.0) {
                float initialSize = (float) abstractClientPlayerEntity.getAttributeBaseValue(BOOB_INITIAL_SIZE);
                double currentTime = Time.currentTime(abstractClientPlayerEntity.world);
                size = boobSize(startTime, currentTime, initialSize, h);
            } else {
                size = 0.0F;
            }
            ((PlayerEntityModelExtension) this.getContextModel()).estrogen$renderBoobs(matrixStack, vertexConsumer, i, m, abstractClientPlayerEntity, size);
            matrixStack.pop();
        }
    }
}