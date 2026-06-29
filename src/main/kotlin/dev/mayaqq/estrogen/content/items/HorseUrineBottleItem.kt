package dev.mayaqq.estrogen.content.items

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.level.Level

class HorseUrineBottleItem(properties: Properties) : Item(properties) {
    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        super.finishUsingItem(stack, level, entity)
        if (entity is ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(entity, stack)
            entity.awardStat(Stats.ITEM_USED.get(this))
        }

        if (stack.isEmpty) {
            return Items.GLASS_BOTTLE.defaultInstance
        } else {
            if (entity is Player && !entity.isCreative) {
                val bottle = ItemStack(Items.GLASS_BOTTLE)
                if (!entity.inventory.add(bottle)) {
                    entity.drop(bottle, false)
                }
            }
        }
        return stack
    }

    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 40
    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.DRINK
    override fun getDrinkingSound(): SoundEvent = SoundEvents.HONEY_DRINK
    override fun getEatingSound(): SoundEvent = SoundEvents.HONEY_DRINK

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> = ItemUtils.startUsingInstantly(level, player, hand)
}