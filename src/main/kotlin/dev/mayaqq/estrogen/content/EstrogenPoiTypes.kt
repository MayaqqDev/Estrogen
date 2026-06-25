package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.blocks.poi.PoiHelpers
import dev.mayaqq.cynosure.blocks.poi.PoiHelpers.registerState
import dev.mayaqq.estrogen.MOD_ID
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.ai.village.poi.PoiType

object EstrogenPoiTypes : Registrar<PoiType> by Registrar(MOD_ID, Registries.POINT_OF_INTEREST_TYPE) {
    val DreamCatcher: PoiType by entry("dreamcatcher", {PoiHelpers.poi(EstrogenBlocks.DreamCatcher)}) {
        onRegister { registerState(it) }
    }
}