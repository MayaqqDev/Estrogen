package dev.mayaqq.estrogen.registry.common.items;

import dev.mayaqq.estrogen.multiplatform.EstrogenPatchesAbstraction;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EstrogenPatchesItem extends EstrogenPatchesAbstraction {
    int tick = 0;

    public EstrogenPatchesItem(Properties properties) {
        super(properties);
    }

    @Override
    public void patchTick(ItemStack stack, int slot, LivingEntity entity) {
        effectTick(stack, entity, false);
    }

    public void effectTick(ItemStack stack, LivingEntity entity, boolean bypass) {
        if (entity instanceof Player player && (bypass || tick == 20)) {
            tick = 0;
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, 400, stack.getCount() - 1, false, false, false));
        }
    }

    @Override
    public void onEquip(ItemStack stack, int slot, LivingEntity entity) {
        effectTick(stack, entity, true);
    }

    @Override
    public void onUnEquip(ItemStack stack, int slot, LivingEntity entity) {
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
