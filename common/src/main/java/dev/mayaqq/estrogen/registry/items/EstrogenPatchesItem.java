package dev.mayaqq.estrogen.registry.items;

import com.simibubi.create.AllEnchantments;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// Currently can't be enchanted with Capacity, broken thing in botarium.
public class EstrogenPatchesItem extends Item implements Bauble, BotariumFluidItem<WrappedItemFluidContainer>/*, CapacityEnchantment.ICapacityEnchantable*/ {
    private final static int TRIGGER_EVERY_X_TICKS = 300;
    private final static int EFFECT_DURATION = TRIGGER_EVERY_X_TICKS + 220;

    public EstrogenPatchesItem(Properties properties) {
        super(properties);
        Baubly.registerBauble(this);
    }

    @Override
    public void tick(ItemStack stack, SlotInfo slot) {
        ItemFluidContainer itemFluidManager = getFluidContainer(stack);
        Level level = slot.wearer().level();
        if (!level.isClientSide && slot.wearer() instanceof Player player) {
            if (level.getGameTime() % TRIGGER_EVERY_X_TICKS == 0) {
                addEffect(player, level);
            }
            if (EstrogenConfig.server().patchDrain.get() && level.getGameTime() % EstrogenConfig.server().patchDrainAmount.get() == 0 && !player.isCreative()) {
                itemFluidManager.extractFromSlot(0, FluidHolder.of(EstrogenFluids.LIQUID_ESTROGEN.get(), FluidConstants.getBucketAmount() / 1000), false);
                itemFluidManager.serialize(stack.getOrCreateTag());
            }
        }
    }

    public void addEffect(Player player, Level level) {
        player.addEffect(new MobEffectInstance(EstrogenEffects.ESTROGEN_EFFECT.get(), EFFECT_DURATION, EstrogenConfig.server().patchGirlPowerAmount.get() -1, false, false, false));
    }

    public long getMaxCapacity(ItemStack stack) {
        return FluidConstants.getBucketAmount() + ((FluidConstants.getBucketAmount() / 2) * EnchantmentHelper.getEnchantments(stack).getOrDefault(AllEnchantments.CAPACITY.get(), 0));
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


    @Override
    public void onEquip(ItemStack stack, SlotInfo slot) {
        Level level = slot.wearer().level();
        if (!level.isClientSide && slot.wearer() instanceof Player player) {
            addEffect(player, level);
        }
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
        return new WrappedItemFluidContainer(stack, new SimpleFluidContainer(getMaxCapacity(stack), 1, (amount, fluid) -> fluid.is(EstrogenFluids.LIQUID_ESTROGEN.get())));
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getAmount(stack) != getMaxCapacity(stack);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return (int) ((double) getAmount(stack) / getMaxCapacity(stack) * 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return EstrogenColors.ESTROGEN_PATCHES_BAR.value;
    }
}
