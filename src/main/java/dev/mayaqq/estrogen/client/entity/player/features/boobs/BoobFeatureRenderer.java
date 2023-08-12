package dev.mayaqq.estrogen.client.entity.player.features.boobs;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
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

@Environment(EnvType.CLIENT)
public class BoobFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public BoobFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {
        if (abstractClientPlayerEntity.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT) && Estrogen.CONFIG.boobies() && abstractClientPlayerEntity.hasSkinTexture() && !abstractClientPlayerEntity.isInvisible()) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getSkinTexture()));
            int m = LivingEntityRenderer.getOverlay(abstractClientPlayerEntity, 0.0F);
            matrixStack.push();
            ((PlayerEntityModelExtension) this.getContextModel()).estrogen$renderBoobs(matrixStack, vertexConsumer, i, m, abstractClientPlayerEntity);
            matrixStack.pop();
        }
    }
}
