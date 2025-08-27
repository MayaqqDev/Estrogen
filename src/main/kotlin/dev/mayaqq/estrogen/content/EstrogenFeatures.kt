package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.features.MemorialFeature
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.feature.Feature
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry

object EstrogenFeatures : Registrar<Feature<*>> by Estrogen..Registries.FEATURE {
    val Memorial: MemorialFeature by entry("memorial", ::MemorialFeature)
}