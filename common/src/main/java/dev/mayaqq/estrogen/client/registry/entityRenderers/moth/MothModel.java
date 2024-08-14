package dev.mayaqq.estrogen.client.registry.entityRenderers.moth;

import dev.mayaqq.estrogen.registry.entities.MothEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class MothModel<T extends MothEntity> extends AgeableHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("estrogen", "mothmodel"), "main");
    private final ModelPart main;
    private final ModelPart wings;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart legs;
    private final ModelPart head;
    private final ModelPart ass;
    private final ModelPart body;

    public MothModel(ModelPart root) {
        super(0.5f, 18);
        this.main = root.getChild("main");
        this.body = main.getChild("body");
        this.wings = body.getChild("wings");
        this.right_wing = wings.getChild("right_wing");
        this.left_wing = wings.getChild("left_wing");
        this.legs = main.getChild("legs");
        this.head = body.getChild("head");
        this.ass = body.getChild("ass");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition legs = main.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition back_legs_r1 = legs.addOrReplaceChild("back_legs_r1", CubeListBuilder.create().texOffs(16, 23).addBox(-3.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(26, 0).addBox(0.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(30, 23).addBox(3.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0009F, 0.0733F, 0.0F, 1.5708F, 0.0F));

        PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -0.0833F));

        PartDefinition body_middle_r1 = body.addOrReplaceChild("body_middle_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.0F, -4.0F, 9.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(20, 35).addBox(-4.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(-2.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(40, 44).addBox(0.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(20, 44).addBox(2.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 44).addBox(4.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0833F, 0.0F, 1.5708F, 0.0F));

        PartDefinition ass = body.addOrReplaceChild("ass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 4.0833F));

        PartDefinition fur7_r1 = ass.addOrReplaceChild("fur7_r1", CubeListBuilder.create().texOffs(44, 25).addBox(-8.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(40, 35).addBox(-6.0F, -13.0F, -5.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-9.0F, -11.0F, -3.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0002F, -4.0367F, 0.0F, 1.5708F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -4.9167F));

        PartDefinition headtop_r1 = head.addOrReplaceChild("headtop_r1", CubeListBuilder.create().texOffs(0, 16).addBox(5.0F, -11.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 29).addBox(5.0F, -10.0F, -3.0F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(6.0F, -13.0F, -5.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 5.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0833F));

        PartDefinition right_wing = wings.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(4.0F, -8.0F, 0.0F));

        PartDefinition right_wing_r1 = right_wing.addOrReplaceChild("right_wing_r1", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.2829F, 3.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1475F, 11.7829F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition left_wing = wings.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(-4.0F, -8.0F, 0.0F));

        PartDefinition left_wing_r1 = left_wing.addOrReplaceChild("left_wing_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -11.5F, -13.0F, 8.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 11.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(MothEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.flyingAnimationState, MothAnimations.FLYING, ageInTicks);
        this.animate(entity.idleAnimationState, MothAnimations.IDLE, ageInTicks);
        this.animate(entity.fuzzUpFlyingAnimationState, MothAnimations.FUZZ_UP, ageInTicks);
        this.animate(entity.fuzzUpIdleAnimationState, MothAnimations.FUZZ_UP_LANDED, ageInTicks);
        this.animate(entity.landingAnimationState, MothAnimations.LAND, ageInTicks);
        this.animate(entity.takingOffAnimationState, MothAnimations.TAKE_OFF, ageInTicks);
    }

    @Override
    public ModelPart root() {
        return this.main;
    }
}