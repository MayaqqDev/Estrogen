package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.content.advancements.triggers.InsertJarTrigger
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectTrigger
import net.minecraft.advancements.CriteriaTriggers

object AdvancementTriggers {
    val InsertJar: InsertJarTrigger = CriteriaTriggers.register(InsertJarTrigger())
    val KilledWithEffect: KilledWithEffectTrigger = CriteriaTriggers.register(KilledWithEffectTrigger())

    fun register() {}
}