package dev.mayaqq.estrogen.registry.items;

import com.simibubi.create.foundation.utility.Color;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenCreateItems;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import earth.terrarium.botarium.common.fluid.base.*;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EstrogenPatchesItem extends Item implements Bauble, BotariumFluidItem<WrappedItemFluidContainer> {
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
        if (info.wearer() instanceof Player player && (bypass || tick == 20) && this.getAmount(stack) > 0) {
            tick = 0;
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 400, stack.getCount() - 1, false, false, false));
        }
        if (EstrogenConfig.server().patchDrain.get() && estrogenAmountTick == EstrogenConfig.server().patchDrainAmount.get()) {
            estrogenAmountTick = 0;
            getFluidHolder(stack).setAmount(Math.max(0, this.getAmount(stack) - 1));
        }
    }

    @Override
    public void onEquip(ItemStack stack, SlotInfo slot) {
        patchTick(stack, slot, true);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotInfo slot) {
        slot.wearer().removeEffect(EstrogenEffects.ESTROGEN_EFFECT.get());
    }

    public @NotNull ItemStack getDefaultInstance() {
        return new ItemStack(this);
    }

    public ItemStack getFullStack() {
        ItemStack newStack = new ItemStack(EstrogenCreateItems.ESTROGEN_PATCHES.get());
        this.getFluidHolder(newStack).setFluid(EstrogenFluids.LIQUID_ESTROGEN.get());
        this.getFluidHolder(newStack).setAmount(1000);
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

    public FluidHolder getFluidHolder(ItemStack stack) {
        return this.getFluidContainer(stack).getFluids().get(0);
    }

    @SuppressWarnings("DataFlowIssue")
    public long getAmount(ItemStack stack) {
        return getFluidHolder(stack).getFluidAmount();
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

    @Override
    public WrappedItemFluidContainer getFluidContainer(ItemStack stack) {
        return  new WrappedItemFluidContainer(stack, new SimpleFluidContainer(1000, 1, (amount, fluid) -> fluid == EstrogenFluids.LIQUID_ESTROGEN.get()));
    }
}
