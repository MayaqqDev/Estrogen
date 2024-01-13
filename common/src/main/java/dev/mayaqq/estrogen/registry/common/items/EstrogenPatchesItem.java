package dev.mayaqq.estrogen.registry.common.items;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EstrogenPatchesItem extends TrinketItem {

    public EstrogenPatchesItem(Item.Properties properties) {
        super(properties);
    }
    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, -1, stack.getCount() - 1, false, false, false));
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.removeEffect(EstrogenEffects.ESTROGEN_EFFECT);
    }

    @Override
    public @NotNull String getDescriptionId(ItemStack stack) {
        int count = stack.getCount();
        if (count == 1) {
            return super.getDescriptionId(stack);
        } else {
            return "item.estrogen.estrogen_patches_plural";
        }
    }
}
