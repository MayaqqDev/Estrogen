package dev.mayaqq.estrogen.content.recipes.inventory

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

data class InteractionData(val item: ItemStack, val entity: Entity, val player: ServerPlayer) : Container {
    override fun clearContent() = throw UnsupportedOperationException()
    override fun getContainerSize(): Int = throw UnsupportedOperationException()
    override fun isEmpty(): Boolean = throw UnsupportedOperationException()
    override fun getItem(slot: Int): ItemStack = throw UnsupportedOperationException()
    override fun removeItem(slot: Int, amount: Int): ItemStack = throw UnsupportedOperationException()
    override fun removeItemNoUpdate(p0: Int): ItemStack = throw UnsupportedOperationException()
    override fun setItem(slot: Int, stack: ItemStack) = throw UnsupportedOperationException()
    override fun setChanged() = throw UnsupportedOperationException()
    override fun stillValid(player: Player): Boolean = throw UnsupportedOperationException()
}
