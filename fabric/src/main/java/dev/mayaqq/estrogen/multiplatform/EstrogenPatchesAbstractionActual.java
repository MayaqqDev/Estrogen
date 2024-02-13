package dev.mayaqq.estrogen.multiplatform;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.msrandom.multiplatform.annotations.Actual;

@Actual
public class EstrogenPatchesAbstractionActual extends TrinketItem {
    @Actual
    public EstrogenPatchesAbstractionActual(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        this.patchTick(stack, slot.index(), entity);
    }

    @Actual
    public void patchTick(ItemStack stack, int slot, LivingEntity entity) {}

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        this.onEquip(stack, slot.index(), entity);
    }

    @Actual
    public void onEquip(ItemStack stack, int slot, LivingEntity entity) {
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        this.onUnEquip(stack, slot.index(), entity);
    }

    @Actual
    public void onUnEquip(ItemStack stack, int slot, LivingEntity entity) {
    }
}