package dev.mayaqq.estrogen.registry.items;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.utils.EstrogenColors;
import earth.terrarium.baubly.Baubly;
import earth.terrarium.baubly.common.Bauble;
import earth.terrarium.baubly.common.SlotInfo;
import earth.terrarium.botarium.common.fluid.FluidConstants;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.ChatFormatting;
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
    int estrogenAmountTick = 0;

    public EstrogenPatchesItem(Properties properties) {
        super(properties);
        Baubly.registerBauble(this);
    }

    @Override
    public void tick(ItemStack stack, SlotInfo slot) {
        patchTick(stack, slot);
        estrogenAmountTick++;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        ItemStackHolder holder = new ItemStackHolder(stack);
        ItemFluidContainer itemFluidManager = FluidContainer.of(holder);
        if (itemFluidManager != null) {
            long amount = FluidConstants.toMillibuckets(itemFluidManager.getFluids().get(0).getFluidAmount());
            long amountCapacity = FluidConstants.toMillibuckets(itemFluidManager.getTankCapacity(0));
            String fluidString = Component.translatable("fluid_type.estrogen.liquid_estrogen").getString();
            tooltipComponents.add(Component.literal(" "));
            tooltipComponents.add(Component.literal(String.format("%s: %smb / %smb", fluidString, amount, amountCapacity)).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
            tooltipComponents.add(Component.literal(" "));
        }
    }

    public void patchTick(ItemStack stack, SlotInfo info) {
        if (info.wearer() instanceof Player player && !stack.isEmpty()) {
            player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), 401, EstrogenConfig.server().patchGirlPowerAmount.get(), false, false, false));
            if (EstrogenConfig.server().patchDrain.get() && estrogenAmountTick >= EstrogenConfig.server().patchDrainAmount.get() && !player.isCreative()) {
                estrogenAmountTick = 0;
                ItemFluidContainer itemFluidManager = getFluidContainer(stack);
                itemFluidManager.extractFromSlot(0, FluidHolder.of(EstrogenFluids.LIQUID_ESTROGEN.get(), FluidConstants.getBucketAmount() / 1000), false);
                itemFluidManager.serialize(stack.getOrCreateTag());
            }
        }
    }

    @Override
    public void onEquip(ItemStack stack, SlotInfo slot) {
        patchTick(stack, slot);
    }

    public ItemStack getFullStack() {
        ItemStack stack = this.getDefaultInstance();
        ItemFluidContainer itemFluidManager = getFluidContainer(stack);
        itemFluidManager.insertFluid(FluidHolder.of(EstrogenFluids.LIQUID_ESTROGEN.get(), FluidConstants.getBucketAmount()), false);
        itemFluidManager.serialize(stack.getOrCreateTag());
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
        return new WrappedItemFluidContainer(stack, new SimpleFluidContainer(FluidConstants.getBucketAmount(), 1, (amount, fluid) -> fluid.is(EstrogenFluids.LIQUID_ESTROGEN.get())));
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
        return EstrogenColors.ESTROGEN_PATCHES_BAR.value;
    }
}
