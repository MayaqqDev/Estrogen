package dev.mayaqq.estrogen.registry.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UwufyCurseEnchantment extends Enchantment {

    public UwufyCurseEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slotTypes) {
        super(rarity, category, slotTypes);
    }
    @Override
    public int getMinCost(int level) {
        return 25;
    }

    @Override
    public int getMaxCost(int level) {
        return 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }
}
