package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.features.thighhighs.ThighHighStyleLootFunction
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.builder.entry
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType

object EstrogenLootFunctions : Registrar<LootItemFunctionType<*>> by Registrar(MOD_ID, Registries.LOOT_FUNCTION_TYPE) {
    val ThighHighLoot: LootItemFunctionType<ThighHighStyleLootFunction> by entry("thigh_high_style", {
        LootItemFunctionType(ThighHighStyleLootFunction.CODEC)
    }) {}
}