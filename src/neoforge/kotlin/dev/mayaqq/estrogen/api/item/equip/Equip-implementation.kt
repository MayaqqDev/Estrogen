package dev.mayaqq.estrogen.api.item.equip

import dev.mayaqq.estrogen.api.item.equip.client.EquipRenderer
import dev.mayaqq.estrogen.api.item.equip.client.RendererCuriosWrapper
import invoke.kitty.kritter.utils.clientOnly
import net.minecraft.world.item.Item
import top.theillusivec4.curios.api.CuriosApi
import top.theillusivec4.curios.api.client.CuriosRendererRegistry

actual fun registerEquip(item: Item, equip: Equip) {
    CuriosApi.registerCurio(item, CuriosWrapper(equip))
}

actual fun registerEquipRenderer(item: Item, equipRenderer: EquipRenderer) = clientOnly {
    CuriosRendererRegistry.register(item) { RendererCuriosWrapper(equipRenderer) }
}