package dev.mayaqq.estrogen.registry.items;

import com.simibubi.create.foundation.utility.Color;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenCreateItems;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.*;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        ItemStackHolder holder = new ItemStackHolder(stack);
        ItemFluidContainer itemFluidManager = FluidContainer.of(holder);
        if (itemFluidManager != null) {
            long amount = FluidConstants.toMillibuckets(itemFluidManager.getFluids().get(0).getFluidAmount());
            long amountCapacity = itemFluidManager.getTankCapacity(0);
            tooltipComponents.add(Component.literal("Liquid Estrogen: " + amount + "mb / " + amountCapacity + "mb").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
        }
    }

    public void patchTick(ItemStack stack, SlotInfo info, boolean bypass) {
        if (info.wearer() instanceof Player player && (bypass || tick == 20) && this.getAmount(stack) > 0) {
            tick = 0;
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 400, stack.getCount() - 1, false, false, false));
        }
        if (EstrogenConfig.server().patchDrain.get() && estrogenAmountTick == EstrogenConfig.server().patchDrainAmount.get()) {
            estrogenAmountTick = 0;
            ItemFluidContainer itemFluidManager = getFluidContainer(stack);
            itemFluidManager.extractFromSlot(0, FluidHolder.of(EstrogenFluids.LIQUID_ESTROGEN.get(), 1), false);
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

    public ItemStack getFullStack() {
        ItemStack stack = this.getDefaultInstance();
        ItemFluidContainer itemFluidManager = getFluidContainer(stack);
        itemFluidManager.insertFluid(FluidHolder.of(EstrogenFluids.LIQUID_ESTROGEN.get(), FluidConstants.getBucketAmount()), false);
        return stack;
    }

    @SuppressWarnings("DataFlowIssue")
    public long getAmount(ItemStack stack) {
        ItemStackHolder holder = new ItemStackHolder(stack);
        ItemFluidContainer itemFluidManager = FluidContainer.of(holder);
        return itemFluidManager.getFluids().get(0).getFluidAmount();
    }

    @Override
    public WrappedItemFluidContainer getFluidContainer(ItemStack stack) {
        return  new WrappedItemFluidContainer(stack, new SimpleFluidContainer(FluidConstants.getBucketAmount(), 1, (amount, fluid) -> fluid == EstrogenFluids.LIQUID_ESTROGEN.get()));
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

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getAmount(stack) != FluidConstants.getBucketAmount();
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return (int) ((double) getAmount(stack) / FluidConstants.getBucketAmount() * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return Color.SPRING_GREEN.getRGB();
    }
}
