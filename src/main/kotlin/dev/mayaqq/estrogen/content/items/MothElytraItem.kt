package dev.mayaqq.estrogen.content.items

import dev.mayaqq.cynosure.items.extensions.DisablesCape
import dev.mayaqq.estrogen.content.EstrogenItems
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ElytraItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent
import java.util.function.Consumer

class MothElytraItem(properties: Properties) : ElytraItem(properties), DisablesCape {
    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (isFlyEnabled(stack) && entity is LivingEntity && entity.getItemBySlot(EquipmentSlot.CHEST) == stack) {
            doVanillaElytraTick(entity, stack)
        }
    }

    private fun doVanillaElytraTick(entity: LivingEntity, stack: ItemStack) {
        val nextRoll = entity.fallFlyingTicks + 1

        if (!entity.level().isClientSide && nextRoll % 10 == 0) {
            if ((nextRoll / 10) % 2 == 0) {
                stack.hurtAndBreak(
                    1,
                    entity
                ) { player: LivingEntity -> player.broadcastBreakEvent(EquipmentSlot.CHEST) }
            }

            entity.gameEvent(GameEvent.ELYTRA_GLIDE)
        }
    }

    //TODO: this is borked in cynosure bc idk im stupid ig
    override fun disablesCape(itemStack: ItemStack, player: AbstractClientPlayer): Boolean = true
    override fun isValidRepairItem(stack: ItemStack, repair: ItemStack): Boolean = repair.item == EstrogenItems.MothFuzz
}