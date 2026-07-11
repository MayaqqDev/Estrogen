package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.estrogen.content.EstrogenParticles
import invoke.kitty.kritter.utils.color.Color
import net.minecraft.client.Minecraft
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import org.joml.Vector2d
import kotlin.random.Random

class DreamingEffect(category: MobEffectCategory, color: Color) : MobEffect(category, color.toInt(), EstrogenParticles.Dreaming) {

    override fun applyEffectTick(entity: LivingEntity, p1: Int): Boolean {
        if (entity.level().isClientSide && entity == Minecraft.getInstance().cameraEntity) {
            val offsetX = Random.nextDouble(-100.0, 100.0)
            val offsetZ = Random.nextDouble(-100.0, 100.0)
            val offsetY = Random.nextDouble(15.0, 150.0)
            if (Vector2d(offsetX, offsetZ).length() > 60.0)
                entity.level().addAlwaysVisibleParticle(
                    EstrogenParticles.FallingStar, true,
                    offsetX + entity.x,
                    offsetY + entity.y,
                    offsetZ + entity.z,
                    0.0, -0.5 + Random.nextDouble(0.0, 0.36), 0.0
                )
        }

        return entity.level().dayTime % 24000L in 12542..23460
    }


    override fun isInstantenous(): Boolean = false

    override fun shouldApplyEffectTickThisTick(p0: Int, p1: Int): Boolean = true
}