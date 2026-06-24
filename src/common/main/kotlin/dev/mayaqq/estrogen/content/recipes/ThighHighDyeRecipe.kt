package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.DyeItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level


class ThighHighDyeRecipe(id: ResourceLocation, category: CraftingBookCategory) : CustomRecipe(id, category) {
    override fun matches(inv: CraftingContainer, level: Level): Boolean {
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

    override fun assemble(inv: CraftingContainer, registryAccess: RegistryAccess): ItemStack {
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
                    val oldPrimary = Color(item.getColor(stack, 0))
                    val oldSecondary = Color(item.getColor(stack, 1))
                    newPrimary = mixColorWithDye(oldPrimary, leftToItem)
                    newSecondary = mixColorWithDye(oldSecondary, rightToItem)
                } else {
                    newPrimary = colorFromDye(leftToItem)
                    newSecondary = colorFromDye(rightToItem)
                }

                val newThighHighsItem = stack.copy()
                item.setColor(newThighHighsItem, newPrimary.toInt(), newSecondary.toInt())
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
        val dyeColors = (dyeStack.item as DyeItem).dyeColor.textureDiffuseColors
        return Color(dyeColors[0], dyeColors[1], dyeColors[2], 1f)
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height == 9
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.THIGH_HIGH_DYE_SERIALIZER
}