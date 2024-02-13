package dev.mayaqq.estrogen.multiplatform;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.msrandom.multiplatform.annotations.Expect;

@Expect
public class EstrogenPatchesAbstraction extends Item {
    public EstrogenPatchesAbstraction(Properties properties) {
        super(properties);
    }

    public void patchTick(ItemStack stack, int slot, LivingEntity entity) {}

    public void onEquip(ItemStack stack, int slot, LivingEntity entity) {}

    public void onUnEquip(ItemStack stack, int slot, LivingEntity entity) {}
}