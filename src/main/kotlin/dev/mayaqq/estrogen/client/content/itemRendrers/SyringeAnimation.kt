package dev.mayaqq.estrogen.client.content.itemRendrers

import dev.mayaqq.cynosure.client.models.poses.CynosureArmAnimation
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

class SyringeAnimation(stack: ItemStack): CynosureArmAnimation(true, true) {
    override fun <T : LivingEntity> HumanoidModel<T>.modifyMainArm(
        entity: LivingEntity,
        arm: ModelPart,
        ageInTicks: Float
    ) {
        // Hands down is 0 0 0
        // xRot: Spin vertical forward, yRot: spin horizontal, zRot: spin vertical sideways

        // Basically duration of the whole animation in ticks
        val ticks = Math.clamp(entity.ticksUsingItem + ageInTicks - ageInTicks.toInt(), 0f, 28f)
        when {
            ticks <= 20f -> {
                // move up
                val angle = Mth.lerp(ticks / 20.0, 0.0, 130.0)
                arm.xRot = -Math.toRadians(angle).toFloat()
            }
            ticks <= 25f -> {
                // Hold up the arm a little bit
                arm.xRot = -Math.toRadians(130.0).toFloat()
            }
            else -> {
                val ticks = ticks - 25
                // Swing down
                val angleX = Mth.lerp(ticks / 3.0, 130.0, 30.0)
                // turn slightly to inject
                val angleY = Mth.lerp(ticks / 3.0, 0.0, 40.0)

                val moveX = Mth.lerp(ticks / 3.0, 0.0, 0.8)

                arm.x += moveX.toFloat()
                arm.xRot = -Math.toRadians(angleX).toFloat()
                arm.yRot = Math.toRadians(angleY).toFloat()
            }
        }
    }

    override fun <T : LivingEntity> HumanoidModel<T>.modifyOffhandArm(
        entity: LivingEntity,
        arm: ModelPart,
        ageInTicks: Float
    ) {

    }
}