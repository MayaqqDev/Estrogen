package dev.mayaqq.estrogen.content.items

import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.EstrogenComponents
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.fluids.item.EstrogenItemFluidStorage
import dev.mayaqq.estrogen.api.item.equip.Equip
import dev.mayaqq.estrogen.api.item.equip.SlotInfo
import dev.mayaqq.estrogen.utils.EstrogenColors
import dev.mayaqq.estrogen.utils.holder
import earth.terrarium.common_storage_lib.context.ItemContext
import earth.terrarium.common_storage_lib.context.impl.IsolatedSlotContext
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext
import earth.terrarium.common_storage_lib.fluid.FluidApi
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts
import earth.terrarium.common_storage_lib.storage.base.CommonStorage
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class EstrogenPatchesItem(properties: Properties) : Item(properties), FluidProvider.Item, Equip {
    fun tick(stack: ItemStack, slot: SlotInfo) {
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
                EstrogenEffects.Estrogen.holder(),
                EFFECT_DURATION,
                EstrogenServerConfig.Patch.girlPowerLevel - 1,
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
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced)
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
        val context = IsolatedSlotContext(stack)
        FluidApi.ITEM.find(stack, context).let { storage ->
            storage?.insert(
                FluidResource.of(EstrogenFluids.LiquidEstrogen.source),
                FluidAmounts.BUCKET,
                false
            )
        }
        return context.resource.toStack()
    }

    override fun getFluids(stack: ItemStack, context: ItemContext): CommonStorage<FluidResource> {
        return EstrogenItemFluidStorage(context, EstrogenComponents.FluidComponent)
    }

    fun getAmount(stack: ItemStack): Long {
        return FluidApi.ITEM.find(stack, ModifyOnlyContext(stack))?.getAmount(0) ?: 0
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