package dev.mayaqq.estrogen.content.recipes.inventory

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

data class FluidData(val fluid: FluidState, val block: BlockState) : RecipeInput {
    override fun getItem(slot: Int): ItemStack = throw UnsupportedOperationException()
    override fun size(): Int = 0
}
