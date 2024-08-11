package dev.mayaqq.estrogen.client.registry.entityRenderers.moth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class MothModel extends EntityModel<MothEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "mothmodel"), "main");
    private final ModelPart ass;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart legs;
    private final ModelPart head;
    private final ModelPart bb_main;

    public MothModel(ModelPart root) {
        this.ass = root.getChild("ass");
        this.left_wing = root.getChild("left_wing");
        this.right_wing = root.getChild("right_wing");
        this.legs = root.getChild("legs");
        this.head = root.getChild("head");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ass = partdefinition.addOrReplaceChild("ass", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition fur7_r1 = ass.addOrReplaceChild("fur7_r1", CubeListBuilder.create().texOffs(44, 25).addBox(-8.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(40, 35).addBox(-6.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-9.0F, -11.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -12.01F, -13.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.01F, 3.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition back_legs_r1 = legs.addOrReplaceChild("back_legs_r1", CubeListBuilder.create().texOffs(16, 23).addBox(-3.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(26, 0).addBox(0.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(30, 23).addBox(3.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition headtop_r1 = head.addOrReplaceChild("headtop_r1", CubeListBuilder.create().texOffs(0, 16).addBox(5.0F, -11.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 29).addBox(5.0F, -10.0F, -3.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(6.0F, -13.0F, -5.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition fur5_r1 = bb_main.addOrReplaceChild("fur5_r1", CubeListBuilder.create().texOffs(20, 35).addBox(-4.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(-2.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(40, 44).addBox(0.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(20, 44).addBox(2.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(4.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -12.0F, -4.0F, 9.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(MothEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ass.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_wing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_wing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}