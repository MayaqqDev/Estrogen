package dev.mayaqq.estrogen.registry.common;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.enchantments.UwufyCurseEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EstrogenEnchantments {

    public static final RegistryEntry<UwufyCurseEnchantment> UWUFYING_CURSE = Estrogen.REGISTRATE.object("uwufy_curse")
            .enchantment(EnchantmentCategory.ARMOR_HEAD, UwufyCurseEnchantment::new)
            .addSlots(EquipmentSlot.HEAD)
            .rarity(Enchantment.Rarity.VERY_RARE)
            .register();

    public static void register() {}
}
