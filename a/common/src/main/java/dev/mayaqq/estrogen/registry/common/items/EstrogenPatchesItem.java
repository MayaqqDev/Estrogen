package dev.mayaqq.estrogen.registry.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EstrogenPatchesItem extends Item /*TrinketItem */{

    public EstrogenPatchesItem(Item.Properties properties) {
        super(properties);
    }
    //TODO idk how to make unique items for each platform, curios on forge is different idk
/*
    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            player.addStatusEffect(new StatusEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, -1, stack.getCount() - 1, false, false, false));
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.removeStatusEffect(EstrogenEffects.ESTROGEN_EFFECT);
    }*/

    @Override
    public String getDescriptionId(ItemStack stack) {
        int count = stack.getCount();
        if (count == 1) {
            return super.getDescriptionId(stack);
        } else {
            return "item.estrogen.estrogen_patches_plural";
        }
    }
}
