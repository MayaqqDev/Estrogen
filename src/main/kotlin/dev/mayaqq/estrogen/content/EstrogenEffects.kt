package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.effects.DreamingEffect
import dev.mayaqq.estrogen.content.effects.EstrogenEffect
import dev.mayaqq.estrogen.utils.EstrogenColors
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory

@Suppress("unused")
object EstrogenEffects : Registrar<MobEffect> by Registrar(MOD_ID, Registries.MOB_EFFECT) {
    @JvmStatic
    val Estrogen: RegistryEntry<MobEffect> = entry("estrogen", {EstrogenEffect(MobEffectCategory.BENEFICIAL, 104164161)}) {}

    val Dreaming: RegistryEntry<MobEffect> = entry("dreaming", fun() = DreamingEffect(MobEffectCategory.NEUTRAL, EstrogenColors.DREAM_BLOCK)) {}
}