package dev.mayaqq.estrogen.registry.common.items;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public class EstrogenPatchesItem extends TrinketItem {

    public EstrogenPatchesItem(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, 1, stack.getCount() - 1, true, false, false));
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.removeStatusEffect(EstrogenEffects.ESTROGEN_EFFECT);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        int count = stack.getCount();
        if (count == 1) {
            return super.getTranslationKey(stack);
        } else {
            return "item.estrogen.estrogen_patches_plural";
        }
    }
}
