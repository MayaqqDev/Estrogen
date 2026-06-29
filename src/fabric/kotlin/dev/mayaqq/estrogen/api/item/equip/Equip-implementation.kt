package dev.mayaqq.estrogen.api.item.equip

import dev.emi.trinkets.api.TrinketsApi
import dev.emi.trinkets.api.client.TrinketRendererRegistry
import dev.mayaqq.estrogen.api.item.equip.client.EquipRenderer
import dev.mayaqq.estrogen.api.item.equip.client.RendererTrinketWrapper
import net.minecraft.world.item.Item

actual fun registerEquip(item: Item, equip: Equip) {
    TrinketsApi.registerTrinket(item, TrinketsWrapper(equip))
}

actual fun registerEquipRenderer(item: Item, renderer: EquipRenderer) {
    TrinketRendererRegistry.registerRenderer(item, RendererTrinketWrapper(renderer))
}