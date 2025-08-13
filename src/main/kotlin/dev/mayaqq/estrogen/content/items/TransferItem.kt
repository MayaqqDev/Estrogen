package dev.mayaqq.estrogen.content.items

import dev.mayaqq.cynosure.core.isModLoaded
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import dev.mayaqq.cynosure.text.Text.sendToAll
import dev.mayaqq.cynosure.text.TextUtils.splitLines
import dev.mayaqq.cynosure.text.TextUtils.splitToWidth
import dev.mayaqq.estrogen.utils.transfer.TransferHelper
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import kotlin.jvm.optionals.getOrNull

class TransferItem(properties: Properties, val new: ResourceLocation) : Item(properties), CustomTooltip {
    override fun inventoryTick(stack: ItemStack, level: Level, entity: Entity, slot: Int, selected: Boolean) {
        if (isModLoaded("createestrogen")) {
            BuiltInRegistries.ITEM.getOptional(new).getOrNull()?.let {
                val slot = entity.getSlot(slot)
                val new = this.defaultInstance
                new.tag = slot.get().tag
                slot.set(new)
            }
        } else if (level.gameTime % 100 == 0L) TransferHelper.message.sendToAll(level)
    }

    override fun MutableList<Component>.modifyTooltip(
        stack: ItemStack,
        player: Player?,
        flags: TooltipFlag
    ) {
        addAll(TransferHelper.message.splitLines())
    }

    override fun getDescriptionId(): String {
        return "estrogen.item.transfer_dummy"
    }
}