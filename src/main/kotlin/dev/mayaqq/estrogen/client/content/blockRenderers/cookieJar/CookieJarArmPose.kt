package dev.mayaqq.estrogen.client.content.blockRenderers.cookieJar

import dev.mayaqq.cynosure.client.models.poses.CynosureArmPose
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

class CookieJarArmPose(stack: ItemStack) : CynosureArmPose(true, true) {

    override fun <T : LivingEntity> HumanoidModel<T>.modifyMainArm(
        entity: LivingEntity,
        arm: ModelPart
    ) {
        arm.xRot = xRot
        arm.yRot = 0F
        arm.zRot = 0F
    }

    override fun <T : LivingEntity> HumanoidModel<T>.modifyOffhandArm(
        entity: LivingEntity,
        arm: ModelPart
    ) {
        arm.xRot = xRot
        arm.yRot = 0F
        arm.zRot = 0F
    }

    companion object {
        val xRot = Math.toRadians(-50.0).toFloat()
    }
}