package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry

object EstrogenPotions : Registrar<Potion> by Estrogen..Registries.POTION {
    val ESTROGEN_POTION = entry("estrogen_potion", {Potion("estrogen", MobEffectInstance(EstrogenEffects.ESTROGEN, 12000))}).register()
}