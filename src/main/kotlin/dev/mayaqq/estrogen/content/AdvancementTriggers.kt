package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.content.advancements.triggers.InsertJarTrigger
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectTrigger
import net.minecraft.advancements.CriteriaTriggers

object AdvancementTriggers {
    val INSERT_JAR = CriteriaTriggers.register(InsertJarTrigger())
    val KILLED_WITH_EFFECT = CriteriaTriggers.register(KilledWithEffectTrigger())

    fun register() {}
}