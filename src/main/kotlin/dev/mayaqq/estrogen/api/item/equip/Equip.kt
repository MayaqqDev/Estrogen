package dev.mayaqq.estrogen.api.item.equip

import com.google.common.collect.Multimap
import dev.mayaqq.estrogen.api.item.equip.client.EquipRenderer
import invoke.kitty.kritter.utils.clientOnly
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.Equipable
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

interface Equip {
    fun tick(stack: ItemStack, slot: SlotInfo) {}
    fun onEquip(stack: ItemStack, slot: SlotInfo) {}
    fun onUnequip(stack: ItemStack, slot: SlotInfo) {}
    fun canEquip(stack: ItemStack, slot: SlotInfo): Boolean = true
    fun canUnequip(stack: ItemStack, slot: SlotInfo): Boolean = true
    fun onBreak(stack: ItemStack, slot: SlotInfo) {
        slot.wearer.breakItem(stack)
    }
    fun getEquipSound(stack: ItemStack, slot: SlotInfo): Holder<SoundEvent> {
        return if (stack.item is Equipable) {
            (stack.item as Equipable).equipSound
        } else {
            SoundEvents.ARMOR_EQUIP_GENERIC
        }
    }

    fun canEquipFromUse(stack: ItemStack, entity: LivingEntity): Boolean = false

    fun getAttributeModifiers(
        default: Multimap<Holder<Attribute>, AttributeModifier>,
        stack: ItemStack,
        slot: SlotInfo,
        id: ResourceLocation
    ): Multimap<Holder<Attribute>, AttributeModifier> = default

    fun getDropRule(stack: ItemStack, slot: SlotInfo): DropRule = DropRule.DEFAULT
}

expect fun registerEquip(item: Item, equip: Equip)

fun <T> T.registerEquip() where T : Item, T : Equip {
    registerEquip(this, this)
}

expect fun registerEquipRenderer(item: Item, renderer: EquipRenderer)

fun Item.registerEquipRenderer(renderer: EquipRenderer) = clientOnly {
    registerEquipRenderer(this, renderer)
}
