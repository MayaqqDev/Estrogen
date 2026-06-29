package dev.mayaqq.estrogen.client.content.entityRenderers.moth

import dev.mayaqq.estrogen.content.entities.MothEntity
import dev.mayaqq.estrogen.id
import net.minecraft.client.model.AgeableHierarchicalModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition

class MothModel(
    root: ModelPart,
    private val main: ModelPart = root.getChild("main"),
    private val body : ModelPart = main.getChild("body"),
    private val wings: ModelPart = body.getChild("wings"),
    private val right_wing: ModelPart = wings.getChild("right_wing"),
    private val left_wing: ModelPart = wings.getChild("left_wing"),
    private val legs: ModelPart = main.getChild("legs"),
    private val head: ModelPart = body.getChild("head"),
    private val ass: ModelPart = body.getChild("ass")
) : AgeableHierarchicalModel<MothEntity>(0.5f, 24f) {


    override fun setupAnim(
        entity: MothEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        this.root().allParts.forEach { obj: ModelPart -> obj.resetPose() }

        this.animate(entity.flyingAnimationState, MothAnimations.FLYING, ageInTicks)
        this.animate(entity.idleAnimationState, MothAnimations.IDLE, ageInTicks)
        this.animate(entity.fuzzUpFlyingAnimationState, MothAnimations.FUZZ_UP, ageInTicks)
        this.animate(entity.fuzzUpIdleAnimationState, MothAnimations.FUZZ_UP_LANDED, ageInTicks)
        this.animate(entity.landingAnimationState, MothAnimations.LAND, ageInTicks)
        this.animate(entity.takingOffAnimationState, MothAnimations.TAKE_OFF, ageInTicks)
    }

    override fun root(): ModelPart {
        return this.main
    }

    companion object {
        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(id("mothmodel"), "main")

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.getRoot()

            val main =
                partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, 0.0f))

            val legs = main.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0f, -1.0f, 0.0f))

            val back_legs_r1 = legs.addOrReplaceChild(
                "back_legs_r1",
                CubeListBuilder.create().texOffs(16, 23)
                    .addBox(-3.0f, -4.0f, -3.0f, 0.0f, 2.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(26, 0).addBox(0.0f, -4.0f, -3.0f, 0.0f, 2.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(30, 23).addBox(3.0f, -4.0f, -3.0f, 0.0f, 2.0f, 6.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 4.0009f, 0.0733f, 0.0f, 1.5708f, 0.0f)
            )

            val body = main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0f, -5.0f, -0.0833f))

            val body_middle_r1 = body.addOrReplaceChild(
                "body_middle_r1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -12.0f, -4.0f, 9.0f, 8.0f, 8.0f, CubeDeformation(0.0f))
                    .texOffs(20, 35).addBox(-4.0f, -13.0f, -5.0f, 0.0f, 10.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 35).addBox(-2.0f, -13.0f, -5.0f, 0.0f, 10.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(40, 44).addBox(0.0f, -13.0f, -5.0f, 0.0f, 10.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(20, 44).addBox(2.0f, -13.0f, -5.0f, 0.0f, 10.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 44).addBox(4.0f, -13.0f, -5.0f, 0.0f, 10.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 8.0f, 0.0833f, 0.0f, 1.5708f, 0.0f)
            )

            val ass = body.addOrReplaceChild("ass", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, 4.0833f))

            val fur7_r1 = ass.addOrReplaceChild(
                "fur7_r1",
                CubeListBuilder.create().texOffs(44, 25)
                    .addBox(-8.0f, -13.0f, -5.0f, 0.0f, 10.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(40, 35).addBox(-6.0f, -13.0f, -5.0f, 0.0f, 10.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 26).addBox(-9.0f, -11.0f, -3.0f, 5.0f, 7.0f, 6.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 8.0002f, -4.0367f, 0.0f, 1.5708f, 0.0f)
            )

            val head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0f, 0.0f, -4.9167f))

            val headtop_r1 = head.addOrReplaceChild(
                "headtop_r1",
                CubeListBuilder.create().texOffs(0, 16).addBox(5.0f, -11.0f, -2.0f, 1.0f, 1.0f, 4.0f, CubeDeformation(0.0f))
                    .texOffs(22, 29).addBox(5.0f, -10.0f, -3.0f, 1.0f, 5.0f, 6.0f, CubeDeformation(0.0f))
                    .texOffs(16, 16).addBox(6.0f, -13.0f, -5.0f, 0.0f, 3.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(0.0f, 8.0f, 5.0f, 0.0f, 1.5708f, 0.0f)
            )

            val wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0f, 5.0f, 0.0833f))

            val right_wing =
                wings.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(4.0f, -8.0f, 0.0f))

            val right_wing_r1 = right_wing.addOrReplaceChild(
                "right_wing_r1",
                CubeListBuilder.create().texOffs(16, 16)
                    .addBox(-4.0f, -12.2829f, 3.0f, 8.0f, 0.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(-4.1475f, 11.7829f, 0.0f, 0.0f, 1.5708f, 0.0f)
            )

            val left_wing =
                wings.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(-4.0f, -8.0f, 0.0f))

            val left_wing_r1 = left_wing.addOrReplaceChild(
                "left_wing_r1",
                CubeListBuilder.create().texOffs(0, 16)
                    .addBox(-4.0f, -11.5f, -13.0f, 8.0f, 0.0f, 10.0f, CubeDeformation(0.0f)),
                PartPose.offsetAndRotation(4.0f, 11.0f, 0.0f, 0.0f, 1.5708f, 0.0f)
            )

            return LayerDefinition.create(meshdefinition, 64, 64)
        }

    }
}