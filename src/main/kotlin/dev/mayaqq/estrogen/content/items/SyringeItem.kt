package dev.mayaqq.estrogen.content.items

import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.EstrogenComponents
import dev.mayaqq.estrogen.content.EstrogenDamageSources
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.utils.EstrogenColors
import dev.mayaqq.estrogen.utils.get
import dev.mayaqq.estrogen.utils.holder
import earth.terrarium.common_storage_lib.context.ItemContext
import earth.terrarium.common_storage_lib.context.impl.IsolatedSlotContext
import earth.terrarium.common_storage_lib.context.impl.ModifyOnlyContext
import earth.terrarium.common_storage_lib.context.impl.PlayerContext
import earth.terrarium.common_storage_lib.fluid.FluidApi
import earth.terrarium.common_storage_lib.fluid.impl.SimpleFluidStorage
import earth.terrarium.common_storage_lib.fluid.util.FluidProvider
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource
import earth.terrarium.common_storage_lib.resources.fluid.util.FluidAmounts
import earth.terrarium.common_storage_lib.storage.base.CommonStorage
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class SyringeItem(properties: Properties) : Item(properties), FluidProvider.Item {
    override fun getFluids(stack: ItemStack, context: ItemContext): CommonStorage<FluidResource> {
        return SimpleFluidStorage(context, EstrogenComponents.FluidComponent, 1, (FluidAmounts.BUCKET / 1000) * EstrogenServerConfig.Syringe.capacity)
            .filter(0) { it.isOf(EstrogenFluids.LiquidEstrogen.get()) }
    }

    override fun onUseTick(level: Level, entity: LivingEntity, stack: ItemStack, remainingUseDuration: Int) {
        val effect = entity.getEffect(EstrogenEffects.Estrogen.holder()) ?: return
        if (level.gameTime % 5 == 0L) hurt(entity)
        if (entity !is Player) return
        val extracted = FluidApi.ITEM.find(stack, PlayerContext.ofHand(entity, entity.usedItemHand))?.get(0)?.extract(
            FluidResource.of(EstrogenFluids.LiquidEstrogen.source),
            FluidAmounts.BUCKET / 1000,
            false
        )
        if (extracted != null && extracted != 0L) {
            entity.addEffect(MobEffectInstance(
                effect.effect,
                effect.duration + EstrogenServerConfig.Syringe.effectIncreaseAmount,
                effect.amplifier,
                effect.isAmbient,
                effect.isVisible,
                effect.showIcon()
            ))
        }
    }

    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        hurt(entity)
        return super.finishUsingItem(stack, level, entity)
    }

    fun hurt(entity: LivingEntity) {
        if (entity.health > 1.0F || EstrogenServerConfig.Syringe.canKill) {
            entity.hurt(EstrogenDamageSources.UNSAFE_MEDICAL.get(entity.level()), EstrogenServerConfig.Syringe.damageAmount)
        }
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        if (getAmount(context.itemInHand) == 0L && context.player?.isCreative != true) {
            return InteractionResult.FAIL
        } else {
            context.player?.startUsingItem(context.hand)
            return InteractionResult.PASS
        }
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        if (getAmount(stack) == 0L && !player.isCreative) {
            return InteractionResultHolder.fail(stack)
        } else {
            player.startUsingItem(hand)
            return InteractionResultHolder.consume(stack)
        }
    }

    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 720000

    fun getFullStack(): ItemStack {
        val stack = this.defaultInstance
        val context = IsolatedSlotContext(stack)
        FluidApi.ITEM.find(stack, context).let { storage ->
            storage?.insert(
                FluidResource.of(EstrogenFluids.LiquidEstrogen.source),
                (FluidAmounts.BUCKET / 1000) * EstrogenServerConfig.Syringe.capacity,
                false
            )
        }
        return context.resource.toStack()
    }

    fun getOneBucketStack(): ItemStack {
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

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        isAdvanced: TooltipFlag
    ) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced)
        val context = ModifyOnlyContext(stack)
        val fluidStorage = FluidApi.ITEM.find(stack, context)?.get(0)
        if (fluidStorage != null) {
            val amount = FluidAmounts.toMillibuckets(fluidStorage.amount)
            val amountCapacity = FluidAmounts.toMillibuckets(fluidStorage.getLimit(fluidStorage.contents.resource()))
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

    fun getAmount(stack: ItemStack): Long {
        return FluidApi.ITEM.find(stack, ModifyOnlyContext(stack))?.getAmount(0) ?: 0
    }

    override fun isBarVisible(stack: ItemStack): Boolean {
        return getAmount(stack) != getMaxCapacity(stack)
    }

    override fun getBarWidth(stack: ItemStack): Int {
        return (getAmount(stack).toDouble() / getMaxCapacity(stack) * 13).toInt()
    }

    fun getMaxCapacity(stack: ItemStack): Long {
        return ModifyOnlyContext(stack).find(FluidApi.ITEM).let {
            it.getLimit(0, it.get(0).resource)
        }
    }

    override fun getBarColor(stack: ItemStack): Int = EstrogenColors.ESTROGEN_PATCHES_BAR.toInt()
}