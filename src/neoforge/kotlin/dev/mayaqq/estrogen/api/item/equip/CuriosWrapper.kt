package dev.mayaqq.estrogen.api.item.equip

import com.google.common.collect.Multimap
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.ItemStack
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurio
import top.theillusivec4.curios.api.type.capability.ICurioItem

data class CuriosWrapper(val equip: Equip) : ICurioItem {
    override fun curioTick(slotContext: SlotContext, stack: ItemStack) {
        equip.tick(stack, slotContext.slotInfo())
    }

    override fun onEquip(slotContext: SlotContext, prevStack: ItemStack, stack: ItemStack) {
        equip.onEquip(stack, slotContext.slotInfo())
    }

    override fun onUnequip(slotContext: SlotContext, newStack: ItemStack, stack: ItemStack) {
        equip.onUnequip(stack, slotContext.slotInfo())
    }

    override fun curioBreak(slotContext: SlotContext, stack: ItemStack) {
        equip.onBreak(stack, slotContext.slotInfo())
    }

    override fun canEquip(slotContext: SlotContext, stack: ItemStack): Boolean {
        return equip.canEquip(stack, slotContext.slotInfo())
    }

    override fun canUnequip(slotContext: SlotContext, stack: ItemStack): Boolean {
        return equip.canUnequip(stack, slotContext.slotInfo())
    }

    override fun getEquipSound(slotContext: SlotContext, stack: ItemStack): ICurio.SoundInfo {
        return ICurio.SoundInfo(equip.getEquipSound(stack, slotContext.slotInfo()).value(), 1F, 1F)
    }

    override fun canEquipFromUse(slotContext: SlotContext, stack: ItemStack): Boolean {
        return equip.canEquipFromUse(stack, slotContext.entity)
    }

    override fun getAttributeModifiers(
        slotContext: SlotContext,
        id: ResourceLocation,
        stack: ItemStack
    ): Multimap<Holder<Attribute>, AttributeModifier> {
        return equip.getAttributeModifiers(super.getAttributeModifiers(slotContext, id, stack), stack, slotContext.slotInfo(), id)
    }

    override fun getDropRule(
        slotContext: SlotContext,
        source: DamageSource,
        recentlyHit: Boolean,
        stack: ItemStack
    ): ICurio.DropRule {
        return when(equip.getDropRule(stack, slotContext.slotInfo())) {
            DropRule.DEFAULT -> ICurio.DropRule.DEFAULT
            DropRule.ALWAYS_DROP -> ICurio.DropRule.ALWAYS_DROP
            DropRule.ALWAYS_KEEP -> ICurio.DropRule.ALWAYS_KEEP
            DropRule.DESTROY -> ICurio.DropRule.DESTROY
        }
    }
}

internal fun SlotContext.slotInfo(): SlotInfo = SlotInfo(this.identifier, this.entity, this.index)