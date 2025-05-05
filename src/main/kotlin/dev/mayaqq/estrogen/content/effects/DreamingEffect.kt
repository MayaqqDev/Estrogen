package dev.mayaqq.estrogen.content.effects

import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.block.ClientDreamBlock
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeMap

class DreamingEffect(category: MobEffectCategory, color: Color) : MobEffect(category, color.toInt()) {

    override fun applyEffectTick(entity: LivingEntity, p1: Int) {
        if (entity.level().dayTime % 24000L !in 13500..22500) entity.removeEffect(this)
    }

    override fun isDurationEffectTick(p0: Int, p1: Int): Boolean = true
}