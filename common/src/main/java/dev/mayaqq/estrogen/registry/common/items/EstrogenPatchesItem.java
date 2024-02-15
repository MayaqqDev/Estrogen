package dev.mayaqq.estrogen.registry.common.items;

import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EstrogenPatchesItem extends Item implements Bauble {
    int tick = 0;

    public EstrogenPatchesItem(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(ItemStack stack, SlotInfo slot) {
        tick++;
        patchTick(stack, slot.wearer(), false);
    }

    public void patchTick(ItemStack stack, LivingEntity entity, boolean bypass) {
        if (entity instanceof Player player && (bypass || tick == 20)) {
            tick = 0;
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, 400, stack.getCount() - 1, false, false, false));
        }
    }

    @Override
    public void onEquip(ItemStack stack, SlotInfo slot) {
        patchTick(stack, slot.wearer(), true);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotInfo slot) {
        slot.wearer().removeEffect(EstrogenEffects.ESTROGEN_EFFECT);
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
