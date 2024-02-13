package dev.mayaqq.estrogen.multiplatform;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.msrandom.multiplatform.annotations.Actual;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Actual
public class EstrogenPatchesAbstractionActual extends Item implements ICurioItem {
    @Actual
    public EstrogenPatchesAbstractionActual(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        this.patchTick(stack, slotContext.index(), slotContext.entity());
    }

    @Actual
    public void patchTick(ItemStack stack, int slot, LivingEntity entity) {}

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        this.onEquip(stack, slotContext.index(), slotContext.entity());
    }

    @Actual
    public void onEquip(ItemStack stack, int slot, LivingEntity entity) {
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        this.onUnEquip(stack, slotContext.index(), slotContext.entity());
    }

    @Actual
    public void onUnEquip(ItemStack stack, int slot, LivingEntity entity) {
    }
}