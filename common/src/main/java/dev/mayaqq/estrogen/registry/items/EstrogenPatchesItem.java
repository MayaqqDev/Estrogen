package dev.mayaqq.estrogen.registry.items;

import com.simibubi.create.foundation.utility.Color;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EstrogenPatchesItem extends Item implements Bauble {
    int tick = 0;
    int estrogenAmountTick = 0;

    public EstrogenPatchesItem(Properties properties) {
        super(properties);
        Baubly.registerBauble(this);
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

    public static ItemStack getDefaultStack() {
        ItemStack newStack = new ItemStack(EstrogenItems.ESTROGEN_PATCHES);
        newStack.getOrCreateTag().putInt("EstrogenAmount", 1000);
        return newStack;
    }

    public static ItemStack emptyStack() {
        ItemStack newStack = new ItemStack(EstrogenItems.ESTROGEN_PATCHES);
        newStack.getOrCreateTag().putInt("EstrogenAmount", 0);
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
        return getAmount(stack) != 1000;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return (int) ((double) getAmount(stack) / 1000 * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return Color.SPRING_GREEN.getRGB();
    }
}
