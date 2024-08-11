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
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "rosymaple"), "main");
    private final ModelPart wings;
    private final ModelPart legs;
    private final ModelPart HEAD;
    private final ModelPart bb_main;

    public MothModel(ModelPart root) {
        this.wings = root.getChild("wings");
        this.legs = root.getChild("legs");
        this.HEAD = root.getChild("HEAD");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wings = partdefinition.addOrReplaceChild("wings", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.0F, 3.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-4.0F, -12.0F, -13.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create().texOffs(30, 23).addBox(3.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(26, 0).addBox(0.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 23).addBox(-3.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition HEAD = partdefinition.addOrReplaceChild("HEAD", CubeListBuilder.create().texOffs(22, 29).addBox(5.0F, -10.0F, -3.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(5.0F, -11.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -4.0F, 9.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-9.0F, -11.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(6.0F, -13.0F, -5.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(MothEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        wings.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        HEAD.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
