package dev.mayaqq.estrogen.client.content.entityRenderers.mothElytra

import com.google.common.collect.ImmutableList
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.injection.getLastFlap
import net.minecraft.client.model.AgeableListModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Pose
import net.minecraft.world.entity.player.Player
import kotlin.math.pow


class MothElytraModel<T : LivingEntity?>(root: ModelPart) : AgeableListModel<T>() {
    private val root: ModelPart = root.getChild("root")
    private val WingR: ModelPart = this.root.getChild("WingR")
    private val WingL: ModelPart = this.root.getChild("WingL")
    private val ForeWingR: ModelPart = this.root.getChild("ForeWingR")
    private val ForeWingL: ModelPart = this.root.getChild("ForeWingL")

    override fun setupAnim(
        entity: T,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        var xRot = 0.2617994f
        var zRot = -0.2617994f
        var yRot = 0.0f

        if (entity!!.isFallFlying) {
            var j = 1.0f
            val vec3 = entity.deltaMovement
            if (vec3.y < 0.0) {
                val vec32 = vec3.normalize()
                j = 1.0f - (-vec32.y).pow(1.5).toFloat()
            }
            xRot = j * 0.34906584f + (1.0f - j) * xRot
            zRot = j * -1.5707964f + (1.0f - j) * zRot

        } else if (entity.pose == Pose.CROUCHING) {
            xRot = 0.6981317f
            zRot = -0.7853982f
            yRot = 0.08726646f
        }

        if (entity is AbstractClientPlayer) {
            entity.elytraRotX += (xRot - entity.elytraRotX) * 0.1f
            entity.elytraRotY += (yRot - entity.elytraRotY) * 0.1f
            entity.elytraRotZ += (zRot - entity.elytraRotZ) * 0.1f
            WingL.xRot = entity.elytraRotX
            WingL.yRot = entity.elytraRotY
            WingL.zRot = entity.elytraRotZ
        } else {
            WingL.xRot = xRot
            WingL.zRot = zRot
            WingL.yRot = yRot
        }

        /*** Flapping these cheeks lol ***/
        if (entity is Player && entity.isFallFlying) {
            val flapDuration = 1000.0f
            val flapStartTime = entity.getLastFlap()
            val flap = (System.currentTimeMillis() - flapStartTime).toFloat()

            if (flap <= flapDuration) {
                val progress = (flap / flapDuration).coerceIn(0.0f, 1.0f)

                WingL.xRot += Mth.cos(progress * Mth.TWO_PI * 1)
            }
        }
        /***                           ***/

        WingR.yRot = -WingL.yRot
        WingR.xRot = WingL.xRot
        WingR.zRot = -WingL.zRot

        ForeWingR.yRot = WingR.yRot * 0.5f
        ForeWingR.xRot = WingR.xRot * 0.5f
        ForeWingR.zRot = WingR.zRot * 0.5f
        ForeWingL.yRot = WingL.yRot * 0.5f
        ForeWingL.xRot = WingL.xRot * 0.5f
        ForeWingL.zRot = WingL.zRot * 0.5f
    }

    override fun renderToBuffer(
        poseStack: PoseStack,
        vertexConsumer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha)
    }

    // I have n o idea what im doing
    private fun flap(x: Double): Double {
        return if (x < 0.2) {
            // SLightly go upwards
            1.0
        } else if(x < 0.7) {
            // drop down
            1.0
        } else {
            // GO back up
            1.0
        }
    }


    override fun headParts(): Iterable<ModelPart> {
        return ImmutableList.of()
    }

    override fun bodyParts(): Iterable<ModelPart> {
        return ImmutableList.of(this.WingL, this.ForeWingL, this.WingR, this.ForeWingR)
    }

    companion object {

        val LAYER_LOCATION: ModelLayerLocation = ModelLayerLocation(id("moth_elytra"), "main")

        fun createBodyLayer(): LayerDefinition {
            val meshdefinition = MeshDefinition()
            val partdefinition = meshdefinition.root

            val root =
                partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0f, 24.0f, -1.0f))

            val WingR =
                root.addOrReplaceChild("WingR", CubeListBuilder.create(), PartPose.offset(-0.5f, -23.0f, 4.075f))

            val OuterWingR_r1 = WingR.addOrReplaceChild(
                "OuterWingR_r1",
                CubeListBuilder.create().texOffs(0, 14)
                    .addBox(-8.9f, -2.4f, -0.525f, 9.0f, 13.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(0, 0).addBox(-8.9f, -2.4f, -0.525f, 9.0f, 13.0f, 1.0f, CubeDeformation(-0.2f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.2824f, -0.2749f, -0.2894f)
            )

            val WingL = root.addOrReplaceChild("WingL", CubeListBuilder.create(), PartPose.offset(0.5f, -23.0f, 4.075f))

            val OuterWingL_r1 = WingL.addOrReplaceChild(
                "OuterWingL_r1",
                CubeListBuilder.create().texOffs(0, 14).mirror()
                    .addBox(-0.1f, -2.4f, -0.525f, 9.0f, 13.0f, 1.0f, CubeDeformation(0.0f)).mirror(false)
                    .texOffs(0, 0).mirror().addBox(-0.1f, -2.4f, -0.525f, 9.0f, 13.0f, 1.0f, CubeDeformation(-0.2f))
                    .mirror(false),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.2824f, 0.2749f, 0.2894f)
            )

            val ForeWingR =
                root.addOrReplaceChild("ForeWingR", CubeListBuilder.create(), PartPose.offset(-3.12f, -21.0f, 2.0f))

            val OuterForeWingR_r1 = ForeWingR.addOrReplaceChild(
                "OuterForeWingR_r1",
                CubeListBuilder.create().texOffs(20, 13)
                    .addBox(-3.23f, -0.9f, 0.0f, 6.0f, 12.0f, 1.0f, CubeDeformation(0.0f))
                    .texOffs(20, 0).addBox(-3.23f, -0.9f, 0.0f, 6.0f, 12.0f, 1.0f, CubeDeformation(-0.2f)),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.1571f, -0.1396f, -0.0812f)
            )

            val ForeWingL =
                root.addOrReplaceChild("ForeWingL", CubeListBuilder.create(), PartPose.offset(3.12f, -21.0f, 2.0f))

            val OuterForeWingL_r1 = ForeWingL.addOrReplaceChild(
                "OuterForeWingL_r1",
                CubeListBuilder.create().texOffs(20, 13).mirror()
                    .addBox(-2.77f, -0.9f, 0.0f, 6.0f, 12.0f, 1.0f, CubeDeformation(0.0f)).mirror(false)
                    .texOffs(20, 0).mirror().addBox(-2.77f, -0.9f, 0.0f, 6.0f, 12.0f, 1.0f, CubeDeformation(-0.2f))
                    .mirror(false),
                PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0.1571f, 0.1396f, 0.0812f)
            )

            return LayerDefinition.create(meshdefinition, 64, 32)
        }
    }
}
