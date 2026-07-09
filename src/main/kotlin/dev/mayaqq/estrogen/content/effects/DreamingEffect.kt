package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.entry.holder
import invoke.kitty.kritter.utils.color.Color
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity

class DreamingEffect(category: MobEffectCategory, color: Color) : MobEffect(category, color.toInt()) {

    override fun applyEffectTick(entity: LivingEntity, p1: Int): Boolean {
        if (entity.level().dayTime % 24000L !in 12542..23460) {
            entity.removeEffect(EstrogenEffects.Dreaming.holder)
            return true
        }
        return false
    }


    override fun isInstantenous(): Boolean = false

    override fun shouldApplyEffectTickThisTick(p0: Int, p1: Int): Boolean = true
}