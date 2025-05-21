package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.cynosure.utils.colors.Color
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity

class DreamingEffect(category: MobEffectCategory, color: Color) : MobEffect(category, color.toInt()) {

    override fun applyEffectTick(entity: LivingEntity, p1: Int) {
        if (entity.level().dayTime % 24000L !in 13500..22500) entity.removeEffect(this)
    }

    override fun isDurationEffectTick(p0: Int, p1: Int): Boolean = true
}