package dev.mayaqq.estrogen.registry.common;

import dev.mayaqq.estrogen.registry.common.enchantments.UwufyCurseEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenEnchantments {

    public static final Enchantment UWUFYING_CURSE = register("uwufy_curse", new UwufyCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.HEAD));

    public static void register() {}

    private static Enchantment register(String id, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, id(id), enchantment);
    }
}
