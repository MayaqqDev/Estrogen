package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.blocks.poi.PoiHelpers
import dev.mayaqq.cynosure.blocks.poi.PoiHelpers.registerState
import dev.mayaqq.estrogen.Estrogen
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.ai.village.poi.PoiType
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry

object EstrogenPoiTypes : Registrar<PoiType> by Estrogen..Registries.POINT_OF_INTEREST_TYPE {
    val DreamCatcher: PoiType by entry("dreamcatcher", {PoiHelpers.poi(EstrogenBlocks.DreamCatcher)}) {
        onRegister {
            registerState(it)
        }
    }
}