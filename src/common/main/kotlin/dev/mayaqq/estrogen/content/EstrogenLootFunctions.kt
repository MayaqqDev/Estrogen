package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.features.thighhighs.ThighHighStyleLootFunction.Serializer
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry

object EstrogenLootFunctions : Registrar<LootItemFunctionType> by Estrogen..Registries.LOOT_FUNCTION_TYPE {
    val ThighHighLoot by entry("thigh_high_style", { LootItemFunctionType(Serializer()) })
}