package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.cynosure.utils.colors.Color
import net.minecraft.server.commands.TimeCommand
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity

class DreamingEffect(category: MobEffectCategory, color: Color) : MobEffect(category, color.toInt()) {

    override fun applyEffectTick(entity: LivingEntity, p1: Int) {
        val time = entity.level().dayTime % 24000L
        if (time in 1..12000) entity.removeEffect(this)
    }
}