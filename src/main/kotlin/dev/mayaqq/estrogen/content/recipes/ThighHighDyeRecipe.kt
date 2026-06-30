package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.cynosure.utils.diffuseColor
import dev.mayaqq.estrogen.content.EstrogenRecipeSerializers
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.toColor
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CraftingInput
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level


class ThighHighDyeRecipe(category: CraftingBookCategory) : CustomRecipe(category) {
    override fun matches(inv: CraftingInput, level: Level): Boolean {
        for (i in 0..2) {
            val leftSlot = i * 3
            val middleSlot = leftSlot + 1
            val rightSlot = leftSlot + 2
            if (inv.getItem(middleSlot).item is ThighHighsItem) {
                val rightToItem = inv.getItem(rightSlot)
                val leftToItem = inv.getItem(leftSlot)

                if (rightToItem.item is DyeItem || leftToItem.item is DyeItem) {
                    for (j in 0..8) {
                        if (j == leftSlot || j == middleSlot || j == rightSlot) {
                            continue
                        }
                        if (!inv.getItem(j).isEmpty) {
                            return false
                        }
                    }

                    val middle = inv.getItem(middleSlot)
                    val thighHighsItem = middle.item as ThighHighsItem

                    // silly expression brackets important dont remove
                    return thighHighsItem.getStyle(middle) == null && (thighHighsItem.hasCustomColor(middle) || rightToItem.item is DyeItem && leftToItem.item is DyeItem)
                }
            }
        }
        return false
    }

    override fun assemble(inv: CraftingInput, lookup: HolderLookup.Provider): ItemStack {
        for (i in 0..2) {
            val leftSlot = i * 3
            val middleSlot = leftSlot + 1
            val rightSlot = leftSlot + 2
            if (inv.getItem(middleSlot).item is ThighHighsItem) {
                val stack = inv.getItem(middleSlot)
                val item = stack.item as ThighHighsItem
                val leftToItem = inv.getItem(leftSlot)
                val rightToItem = inv.getItem(rightSlot)

                val newPrimary: Color
                val newSecondary: Color

                if (item.hasCustomColor(stack)) {
                    val oldPrimary = (item.getColor(stack, 0)).toColor()
                    val oldSecondary = (item.getColor(stack, 1)).toColor()
                    newPrimary = mixColorWithDye(oldPrimary, leftToItem)
                    newSecondary = mixColorWithDye(oldSecondary, rightToItem)
                } else {
                    newPrimary = colorFromDye(leftToItem)
                    newSecondary = colorFromDye(rightToItem)
                }

                val newThighHighsItem = stack.copy()
                item.setColor(newThighHighsItem, newPrimary, newSecondary)
                return newThighHighsItem
            }
        }
        return ItemStack.EMPTY
    }

    private fun mixColorWithDye(original: Color, dyeStack: ItemStack): Color {
        if (dyeStack.isEmpty || dyeStack.item !is DyeItem) return original
        val dyeColor: Color = colorFromDye(dyeStack)
        return original.mix(dyeColor, .5f)
    }

    private fun colorFromDye(dyeStack: ItemStack): Color {
        return (dyeStack.item as DyeItem).dyeColor.diffuseColor
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height == 9
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipeSerializers.THIGH_HIGH_DYE_SERIALIZER.value!!
}