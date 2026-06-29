package dev.mayaqq.estrogen.api.item.equip

import com.google.common.collect.Multimap
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.Trinket
import dev.emi.trinkets.api.TrinketEnums
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.ItemStack

data class TrinketsWrapper(val equip: Equip) : Trinket {
    override fun tick(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
        equip.tick(stack, slot.slotInfo(entity))
    }

    override fun onEquip(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
        equip.onEquip(stack, slot.slotInfo(entity))
    }

    override fun onUnequip(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
        equip.onUnequip(stack, slot.slotInfo(entity))
    }

    override fun onBreak(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
        equip.onBreak(stack, slot.slotInfo(entity))
    }

    override fun canEquip(stack: ItemStack, slot: SlotReference, entity: LivingEntity): Boolean {
        return equip.canEquip(stack, slot.slotInfo(entity))
    }

    override fun canUnequip(stack: ItemStack, slot: SlotReference, entity: LivingEntity): Boolean {
        return equip.canUnequip(stack, slot.slotInfo(entity))
    }

    override fun getEquipSound(stack: ItemStack, slot: SlotReference, entity: LivingEntity): Holder<SoundEvent> {
        return equip.getEquipSound(stack, slot.slotInfo(entity))
    }

    override fun canEquipFromUse(stack: ItemStack, entity: LivingEntity): Boolean {
        return equip.canEquipFromUse(stack, entity)
    }

    override fun getModifiers(
        stack: ItemStack,
        slot: SlotReference,
        entity: LivingEntity,
        slotIdentifier: ResourceLocation
    ): Multimap<Holder<Attribute>, AttributeModifier> {
        return equip.getAttributeModifiers(
            super.getModifiers(stack, slot, entity, slotIdentifier),
            stack,
            slot.slotInfo(entity),
            slotIdentifier
        )
    }

    override fun getDropRule(
        stack: ItemStack,
        slot: SlotReference,
        entity: LivingEntity
    ): TrinketEnums.DropRule {
        return when(equip.getDropRule(stack, slot.slotInfo(entity))) {
            DropRule.DEFAULT -> TrinketEnums.DropRule.DEFAULT
            DropRule.ALWAYS_DROP -> TrinketEnums.DropRule.DROP
            DropRule.ALWAYS_KEEP -> TrinketEnums.DropRule.KEEP
            DropRule.DESTROY -> TrinketEnums.DropRule.DESTROY
        }
    }
}

internal fun SlotReference.slotInfo(entity: LivingEntity): SlotInfo {
    val type = this.inventory.slotType
    return SlotInfo("${type.group}/${type.name}", entity, index)
}
