package dev.mayaqq.estrogen.content.items

import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.utils.EstrogenColors
import earth.terrarium.baubly.common.Bauble
import earth.terrarium.baubly.common.SlotInfo
import earth.terrarium.botarium.common.fluid.FluidConstants
import earth.terrarium.botarium.common.fluid.base.BotariumFluidItem
import earth.terrarium.botarium.common.fluid.base.FluidContainer
import earth.terrarium.botarium.common.fluid.base.FluidHolder
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer
import earth.terrarium.botarium.common.item.ItemStackHolder
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level


class EstrogenPatchesItem(properties: Properties) : Item(properties), Bauble, BotariumFluidItem<WrappedItemFluidContainer> {
    override fun tick(stack: ItemStack, slot: SlotInfo) {
        val itemFluidManager: ItemFluidContainer = getFluidContainer(stack)
        val level: Level = slot.wearer().level()
        if (!level.isClientSide && slot.wearer() is Player && itemFluidManager.fluids[0].fluidAmount > 0) {
            val player = slot.wearer() as Player
            if (level.gameTime % TRIGGER_EVERY_X_TICKS == 0L) {
                addEffect(player, level)
            }
            if (EstrogenServerConfig.Patch.drain && level.gameTime % EstrogenServerConfig.Patch.drainSpeed == 0L && !player.isCreative) {
                itemFluidManager.extractFromSlot(
                    0,
                    FluidHolder.of(EstrogenFluids.LiquidEstrogen.source, FluidConstants.getBucketAmount() / 1000),
                    false
                )
                itemFluidManager.serialize(stack.getOrCreateTag())
            }
        }
    }

    fun addEffect(player: Player, level: Level?) {
        player.addEffect(
            MobEffectInstance(
                EstrogenEffects.Estrogen,
                EFFECT_DURATION,
                EstrogenServerConfig.Patch.girlPowerLevel,
                false,
                false,
                false
            )
        )
    }

    //TODO: Create Capacity (it is broken anyway but well..)
    fun getMaxCapacity(stack: ItemStack): Long = FluidConstants.getBucketAmount() /* + ((FluidConstants.getBucketAmount() / 2) * EnchantmentHelper.getEnchantments(
            stack
        ).getOrDefault(AllEnchantments.CAPACITY.get(), 0)) */

    override fun appendHoverText(
        stack: ItemStack,
        level: Level?,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced)
        val holder = ItemStackHolder(stack)
        val itemFluidManager = FluidContainer.of(holder)
        if (itemFluidManager != null) {
            val amount = FluidConstants.toMillibuckets(itemFluidManager.fluids[0].fluidAmount)
            val amountCapacity = FluidConstants.toMillibuckets(itemFluidManager.getTankCapacity(0))
            val fluidString: String? = Component.translatable("fluid_type.estrogen.liquid_estrogen").string
            tooltipComponents.add(Component.literal(" "))
            tooltipComponents.add(
                Component.literal(
                    String.format(
                        "%s: %smb / %smb",
                        fluidString,
                        amount,
                        amountCapacity
                    )
                ).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
            )
            tooltipComponents.add(Component.literal(" "))
        }
    }


    public override fun onEquip(stack: ItemStack, slot: SlotInfo) {
        val level: Level = slot.wearer().level()
        val itemFluidManager: ItemFluidContainer = getFluidContainer(stack)
        if (!level.isClientSide && slot.wearer() is Player && itemFluidManager.fluids[0].fluidAmount > 0) {
            addEffect(slot.wearer() as Player, level)
        }
    }

    fun getFullStack(): ItemStack {
        val stack = this.defaultInstance
        val itemFluidManager: ItemFluidContainer = getFluidContainer(stack)
        itemFluidManager.insertFluid(
            FluidHolder.of(
                EstrogenFluids.LiquidEstrogen.source,
                FluidConstants.getBucketAmount()
            ), false
        )
        itemFluidManager.serialize(stack.getOrCreateTag())
        return stack
    }

    fun getAmount(stack: ItemStack?): Long = FluidContainer.of(ItemStackHolder(stack)).fluids[0].fluidAmount

    override fun getFluidContainer(stack: ItemStack): WrappedItemFluidContainer {
        return WrappedItemFluidContainer(
            stack,
            SimpleFluidContainer(
                getMaxCapacity(stack),
                1
            ) { amount: Int, fluid: FluidHolder? -> fluid?.`is`(EstrogenFluids.LiquidEstrogen.source) == true }
        )
    }

    override fun isBarVisible(stack: ItemStack): Boolean {
        return getAmount(stack) != getMaxCapacity(stack)
    }

    override fun getBarWidth(stack: ItemStack): Int {
        return (getAmount(stack).toDouble() / getMaxCapacity(stack) * 13).toInt()
    }

    override fun getBarColor(stack: ItemStack): Int = EstrogenColors.ESTROGEN_PATCHES_BAR.toInt()

    companion object {
        private const val TRIGGER_EVERY_X_TICKS: Int = 300
        private const val EFFECT_DURATION: Int = TRIGGER_EVERY_X_TICKS + 220
    }
}