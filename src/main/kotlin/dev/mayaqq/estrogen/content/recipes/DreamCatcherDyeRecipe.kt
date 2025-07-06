package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level


class DreamCatcherDyeRecipe(id: ResourceLocation, category: CraftingBookCategory) : CustomRecipe(id, category) {
    override fun matches(inv: CraftingContainer, level: Level): Boolean {
        val items = inv.items
        items.firstOrNull {it.item is DreamCatcherItem}?.let { stack ->
            if (items.filter { it.item is DyeItem }.map { (it.item as DyeItem).dyeColor }.size == 3) {
                return true
            }
        }
        return false
    }

    override fun assemble(inv: CraftingContainer, registryAccess: RegistryAccess): ItemStack {
        val items = inv.items
        items.firstOrNull {it.item is DreamCatcherItem}?.let { stack ->
            val item = stack.item as DreamCatcherItem
            val dyes: List<DyeColor> = items.filter { it.item is DyeItem }.map { (it.item as DyeItem).dyeColor }

            if (dyes.size == 3) {
                val new = stack.copyWithCount(1)
                item.setDyes(new, dyes[0], dyes[1], dyes[2])
                return new
            }
        }
        return ItemStack.EMPTY
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height >= 4
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.DREAMCATCHER_DYE_SERIALIZER
}