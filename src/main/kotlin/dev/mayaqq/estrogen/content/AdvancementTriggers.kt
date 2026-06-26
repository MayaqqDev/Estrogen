package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.advancements.triggers.InsertJarTrigger
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectTrigger
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.advancements.CriterionTrigger
import net.minecraft.core.registries.Registries

object AdvancementTriggers : Registrar<CriterionTrigger<*>> by Registrar(MOD_ID, Registries.TRIGGER_TYPE) {
    val InsertJar: InsertJarTrigger  by entry("insert_jar", {InsertJarTrigger()}) {}
    val KilledWithEffect: KilledWithEffectTrigger by entry("killed_with_effect", {KilledWithEffectTrigger()}) {}
}