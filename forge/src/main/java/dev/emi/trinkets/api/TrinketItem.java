package dev.emi.trinkets.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/* God forbit me as I have sinned. */
public class TrinketItem extends Item {
    public TrinketItem(Properties arg) {
        super(arg);
    }

    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
    }

    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
    }
}
