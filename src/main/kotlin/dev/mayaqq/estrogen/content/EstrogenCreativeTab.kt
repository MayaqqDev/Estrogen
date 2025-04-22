package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.CreativeModeTab
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.stdlib.creativeTab

object EstrogenCreativeTab : Registrar<CreativeModeTab> by Estrogen..Registries.CREATIVE_MODE_TAB {
    val ESTROGEN by creativeTab("estrogen") {
        icon { EstrogenItems.ESTROGEN_PILL.defaultInstance }
    }
}