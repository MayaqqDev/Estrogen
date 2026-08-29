package dev.mayaqq.estrogen.client.features.boobs

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack

actual fun ArmorItem.getDefaultTexture(stack: ItemStack, entity: Entity, slot: EquipmentSlot, layer: ArmorMaterial.Layer?, overlay: Boolean): String {
    var forgeResult: ResourceLocation? = null
    // NeoForge really needs the layer, sometimes stuff doesn't have it... idkkk
    try {
        forgeResult = this.getArmorTexture(stack, entity, slot, layer, overlay)
    } catch (_: NullPointerException) {}
    return if (forgeResult != null) "${forgeResult.namespace}:${forgeResult.path}" else BoobArmorHandling.default(this, stack, overlay, BuiltInRegistries.ITEM.getKey(this).namespace)
}