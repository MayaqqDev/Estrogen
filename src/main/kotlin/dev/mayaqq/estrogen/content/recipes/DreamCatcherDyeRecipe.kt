package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.estrogen.content.EstrogenRecipeSerializers
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import dev.mayaqq.estrogen.utils.TriColor
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level


class DreamCatcherDyeRecipe(category: CraftingBookCategory) : CustomRecipe(category) {
    override fun matches(inv: CraftingInput, level: Level): Boolean {
        val items = inv.items()
        items.firstOrNull {it.item is DreamCatcherItem}?.let { stack ->
            if (items.filter { it.item is DyeItem }.map { (it.item as DyeItem).dyeColor }.size == 3) {
                return true
            }
        }
        return false
    }

    override fun assemble(inv: CraftingInput, registryAccess: HolderLookup.Provider): ItemStack {
        val items = inv.items()
        items.firstOrNull {it.item is DreamCatcherItem}?.let { stack ->
            val item = stack.item as DreamCatcherItem
            val dyes: List<ItemStack> = items.filter { it.item is DyeItem }

            if (dyes.size == 3) {
                val new = stack.copyWithCount(1)
                val newTriColor: TriColor = if (item.isBlank(new)) {
                    TriColor.fromDyes(dyes)
                } else {
                    item.triColor(new)!!.mix(TriColor.fromDyes(dyes))
                }

                item.setDyes(new, newTriColor)

                return new
            }
        }
        return ItemStack.EMPTY
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height >= 4
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipeSerializers.DREAMCATCHER_DYE_SERIALIZER.value!!

}