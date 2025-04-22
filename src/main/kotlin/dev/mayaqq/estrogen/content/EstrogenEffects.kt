package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.effects.EstrogenEffect
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry

@Suppress("unused")
object EstrogenEffects : Registrar<MobEffect> by Estrogen..Registries.MOB_EFFECT {
    @JvmStatic
    val ESTROGEN: EstrogenEffect by entry("estrogen", {EstrogenEffect(MobEffectCategory.BENEFICIAL, 104164161)})
}