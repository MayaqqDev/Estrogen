package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.content.advancements.triggers.InsertJarTrigger
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectTrigger
import net.minecraft.advancements.CriteriaTriggers

object AdvancementTriggers {
    val InsertJar = CriteriaTriggers.register(InsertJarTrigger())
    val KilledWithEffect = CriteriaTriggers.register(KilledWithEffectTrigger())

    fun register() {}
}