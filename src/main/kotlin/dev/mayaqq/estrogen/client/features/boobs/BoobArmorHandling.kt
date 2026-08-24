package dev.mayaqq.estrogen.client.features.boobs

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack
import java.util.Locale

expect fun ArmorItem.getDefaultTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, layer: ArmorMaterial.Layer, overlay: Boolean): String

object BoobArmorHandling {
    fun getDefaultTexture(item: ArmorItem, stack: ItemStack, entity: Entity, slot: EquipmentSlot, layer: ArmorMaterial.Layer, overlay: Boolean): String {
        return item.getDefaultTexture(stack, entity, slot, layer, overlay)
    }

    fun default(item: ArmorItem, stack: ItemStack, overlay: Boolean, namespace: String): String {
        var texture = item.getMaterial().registeredName
        val idx = texture.indexOf(':')
        if (idx != -1) {
            texture = texture.substring(idx + 1)
        }
        return String.format(
            Locale.ROOT,
            "%s:textures/models/armor/%s_layer_1%s.png",
            namespace,
            texture,
            if (overlay) "_overlay" else ""
        )
    }
}