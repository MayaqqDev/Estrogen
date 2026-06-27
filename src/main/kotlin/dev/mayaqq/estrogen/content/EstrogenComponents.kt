package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.utils.TriColor
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries

object EstrogenComponents : Registrar<DataComponentType<*>> by Registrar(MOD_ID, Registries.DATA_COMPONENT_TYPE) {
    val TriColorComponent: DataComponentType<TriColor> by entry("colors",
        DataComponentType.builder<TriColor>().persistent(TriColor.CODEC)::build
    ) {}
}