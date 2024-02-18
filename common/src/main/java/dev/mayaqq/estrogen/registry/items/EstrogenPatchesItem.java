package dev.mayaqq.estrogen.registry.items;

import com.simibubi.create.foundation.utility.Color;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EstrogenPatchesItem extends Item implements Bauble {
    int tick = 0;
    int estrogenAmountTick = 0;

    public EstrogenPatchesItem(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(ItemStack stack, SlotInfo slot) {
        patchTick(stack, slot,false);
        tick++;
        estrogenAmountTick++;
    }

    public void patchTick(ItemStack stack, SlotInfo info, boolean bypass) {
        CompoundTag tag = stack.getOrCreateTag();
        if (info.wearer() instanceof Player player && (bypass || tick == 20) && tag.getInt("EstrogenAmount") > 0) {
            tick = 0;
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT, 400, stack.getCount() - 1, false, false, false));
        }
        if (EstrogenConfig.server().patchDrain.get() && estrogenAmountTick == EstrogenConfig.server().patchDrainAmount.get()) {
            estrogenAmountTick = 0;
            tag.putInt("EstrogenAmount", Math.max(0, getAmount(stack) - 1));
        }
    }


    @Override
    public void onEquip(ItemStack stack, SlotInfo slot) {
        patchTick(stack, slot, true);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotInfo slot) {
        slot.wearer().removeEffect(EstrogenEffects.ESTROGEN_EFFECT);
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack newStack = new ItemStack(this);
        newStack.getOrCreateTag().putInt("EstrogenAmount", 1000);
        return newStack;
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

    @SuppressWarnings("DataFlowIssue")
    public int getAmount(ItemStack stack) {
        if (!stack.hasTag()) return 0;
        CompoundTag tag = stack.getTag();
        if (!tag.contains("EstrogenAmount")) return 0;
        return tag.getInt("EstrogenAmount");
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return getAmount(stack) / 10 * 13;
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return Color.mixColors(0xFF_FFC074, 0xFF_46FFE0, getAmount(stack) / 10f);
    }
}
