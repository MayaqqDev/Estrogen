package dev.mayaqq.estrogen.content.items

import dev.mayaqq.cynosure.items.extensions.DisablesCape
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.c2s.FlapPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.player.AbstractClientPlayer
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ElytraItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.Vec3

class MothElytraItem(properties: Properties) : ElytraItem(properties), DisablesCape {

    private var flaps = 3
    private var cooldown = 20

    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slotId: Int, isSelected: Boolean) {
        if (cooldown > 0) cooldown--
        if (entity is LivingEntity && !entity.isFallFlying) {
            cooldown = 20
        }
        if (entity.onGround()) {
            flaps = 3
        }
        if (isFlyEnabled(stack) && entity is LivingEntity && entity.getItemBySlot(EquipmentSlot.CHEST) == stack) {
            doVanillaElytraTick(entity, stack)
            if (level.isClientSide() && entity is LocalPlayer && entity.isFallFlying) {
                if (Minecraft.getInstance().options.keyJump.consumeClick() && flaps > 0 && cooldown <= 0) {
                    flaps--
                    cooldown = 20
                    entity.addDeltaMovement(Vec3(0.0, 0.7, 0.0))
                    EstrogenNetwork.sendToServer(FlapPacket(flaps))
                }
            }
        }
    }

    private fun doVanillaElytraTick(entity: LivingEntity, stack: ItemStack) {
        val nextRoll = entity.fallFlyingTicks + 1

        if (!entity.level().isClientSide && nextRoll % 10 == 0) {
            if ((nextRoll / 10) % 2 == 0) {
                stack.hurtAndBreak(1, entity, EquipmentSlot.CHEST);
            }

            entity.gameEvent(GameEvent.ELYTRA_GLIDE)
        }
    }

    //This is still borked in cynosure, but at least the tag works.
    override fun disablesCape(itemStack: ItemStack, player: AbstractClientPlayer): Boolean = true
    override fun isValidRepairItem(stack: ItemStack, repair: ItemStack): Boolean = repair.item == EstrogenItems.MothFuzz.value!!
}