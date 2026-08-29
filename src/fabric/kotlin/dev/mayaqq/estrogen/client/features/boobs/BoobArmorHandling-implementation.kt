package dev.mayaqq.estrogen.client.features.boobs

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack

actual fun ArmorItem.getDefaultTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, layer: ArmorMaterial.Layer?, overlay: Boolean): String {
    return BoobArmorHandling.default(this, stack, overlay, "minecraft")
}