package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion

object EstrogenPotions : Registrar<Potion> by Registrar(MOD_ID, Registries.POTION) {
    val EstrogenPotion = entry("estrogen_potion", {Potion("estrogen", MobEffectInstance(EstrogenEffects.Estrogen.holder(), 12000))}) {}
}