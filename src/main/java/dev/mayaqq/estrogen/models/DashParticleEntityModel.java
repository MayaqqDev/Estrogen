package dev.mayaqq.estrogen.models;

import dev.mayaqq.estrogen.entities.DashParticleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class DashParticleEntityModel extends EntityModel<DashParticleEntity> {

    private final ModelPart bb_main;
    public DashParticleEntityModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -32.0F, -8.0F, 2.0F, 32.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 19).cuboid(-1.0F, -3.0F, -8.0F, 2.0F, 3.0F, 16.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -32.0F, -8.0F, 2.0F, 3.0F, 16.0F, new Dilation(0.0F))
                .uv(36, 0).cuboid(-1.0F, -29.0F, 5.0F, 2.0F, 26.0F, 3.0F, new Dilation(0.0F))
                .uv(33, 35).cuboid(-1.0F, -29.0F, -8.0F, 2.0F, 26.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(DashParticleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
