package dev.mayaqq.estrogen.content.recipes.inventory

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput

data class InteractionData(val item: ItemStack, val entity: Entity, val player: ServerPlayer) : RecipeInput {
    override fun getItem(slot: Int): ItemStack = throw UnsupportedOperationException()
    override fun size(): Int = 0

}
