package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.features.MemorialFeature
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.feature.Feature

object EstrogenFeatures : Registrar<Feature<*>> by Registrar(MOD_ID, Registries.FEATURE) {
    val Memorial: MemorialFeature by entry("memorial", ::MemorialFeature)
}