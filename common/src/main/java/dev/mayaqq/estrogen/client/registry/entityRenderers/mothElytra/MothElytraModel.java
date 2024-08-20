package dev.mayaqq.estrogen.client.registry.entityRenderers.mothElytra;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.phys.Vec3;

public class MothElytraModel<T extends LivingEntity> extends AgeableListModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Estrogen.id("moth_elytra"), "main");
    private final ModelPart root;
    private final ModelPart WingR;
    private final ModelPart WingL;
    private final ModelPart ForeWingR;
    private final ModelPart ForeWingL;

    public MothElytraModel(ModelPart root) {
        this.root = root.getChild("root");
        this.WingR = this.root.getChild("WingR");
        this.WingL = this.root.getChild("WingL");
        this.ForeWingR = this.root.getChild("ForeWingR");
        this.ForeWingL = this.root.getChild("ForeWingL");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));

        PartDefinition WingR = root.addOrReplaceChild("WingR", CubeListBuilder.create(), PartPose.offset(-0.5F, -23.0F, 4.075F));

        PartDefinition OuterWingR_r1 = WingR.addOrReplaceChild("OuterWingR_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-8.9F, -2.4F, -0.525F, 9.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.9F, -2.4F, -0.525F, 9.0F, 13.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2824F, -0.2749F, -0.2894F));

        PartDefinition WingL = root.addOrReplaceChild("WingL", CubeListBuilder.create(), PartPose.offset(0.5F, -23.0F, 4.075F));

        PartDefinition OuterWingL_r1 = WingL.addOrReplaceChild("OuterWingL_r1", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(-0.1F, -2.4F, -0.525F, 9.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-0.1F, -2.4F, -0.525F, 9.0F, 13.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2824F, 0.2749F, 0.2894F));

        PartDefinition ForeWingR = root.addOrReplaceChild("ForeWingR", CubeListBuilder.create(), PartPose.offset(-3.12F, -21.0F, 2.0F));

        PartDefinition OuterForeWingR_r1 = ForeWingR.addOrReplaceChild("OuterForeWingR_r1", CubeListBuilder.create().texOffs(20, 13).addBox(-3.23F, -0.9F, 0.0F, 6.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-3.23F, -0.9F, 0.0F, 6.0F, 12.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1571F, -0.1396F, -0.0812F));

        PartDefinition ForeWingL = root.addOrReplaceChild("ForeWingL", CubeListBuilder.create(), PartPose.offset(3.12F, -21.0F, 2.0F));

        PartDefinition OuterForeWingL_r1 = ForeWingL.addOrReplaceChild("OuterForeWingL_r1", CubeListBuilder.create().texOffs(20, 13).mirror().addBox(-2.77F, -0.9F, 0.0F, 6.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 0).mirror().addBox(-2.77F, -0.9F, 0.0F, 6.0F, 12.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1571F, 0.1396F, 0.0812F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float xRot = 0.2617994f;
        float zRot = -0.2617994f;
        float yRot = 0.0f;
        if (entity.isFallFlying()) {
            float j = 1.0f;
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.y < 0.0) {
                Vec3 vec32 = vec3.normalize();
                j = 1.0f - (float)Math.pow(-vec32.y, 1.5);
            }
            xRot = j * 0.34906584f + (1.0f - j) * xRot;
            zRot = j * -1.5707964f + (1.0f - j) * zRot;
        } else if (entity.getPose() == Pose.CROUCHING) {
            xRot = 0.6981317f;
            zRot = -0.7853982f;
            yRot = 0.08726646f;
        }
        if (entity instanceof AbstractClientPlayer abstractClientPlayer) {
            abstractClientPlayer.elytraRotX += (xRot - abstractClientPlayer.elytraRotX) * 0.1f;
            abstractClientPlayer.elytraRotY += (yRot - abstractClientPlayer.elytraRotY) * 0.1f;
            abstractClientPlayer.elytraRotZ += (zRot - abstractClientPlayer.elytraRotZ) * 0.1f;
            this.WingL.xRot = abstractClientPlayer.elytraRotX;
            this.WingL.yRot = abstractClientPlayer.elytraRotY;
            this.WingL.zRot = abstractClientPlayer.elytraRotZ;
        } else {
            this.WingL.xRot = xRot;
            this.WingL.zRot = zRot;
            this.WingL.yRot = yRot;
        }
        this.WingR.yRot = -this.WingL.yRot;
        this.WingR.xRot = this.WingL.xRot;
        this.WingR.zRot = -this.WingL.zRot;

        this.ForeWingR.yRot = this.WingR.yRot * 0.5f;
        this.ForeWingR.xRot = this.WingR.xRot * 0.5f;
        this.ForeWingR.zRot = this.WingR.zRot * 0.5f;
        this.ForeWingL.yRot = this.WingL.yRot * 0.5f;
        this.ForeWingL.xRot = this.WingL.xRot * 0.5f;
        this.ForeWingL.zRot = this.WingL.zRot * 0.5f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.WingL, this.ForeWingL, this.WingR, this.ForeWingR);
    }
}
