package dev.mayaqq.estrogen.content.recipes.inventory

import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

data class FluidData(val fluid: FluidState, val block: BlockState) : Container {
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
