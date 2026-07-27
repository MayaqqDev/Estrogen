package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.EstrogenRecipeSerializers
import dev.mayaqq.estrogen.content.items.ThighHighsItem
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.asColor
import invoke.kitty.kritter.utils.extensions.isOf
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
        inv.columns().let { columns ->
            if (columns.size < 2) return false

            val thighHighs = columns[1].filter { it isOf EstrogenItems.ThighHighs }.apply {
                if (this.size != 1) return false
            }.first()

            val thighHighsItem = thighHighs.item as ThighHighsItem

            if (thighHighsItem.getStyle(thighHighs) != null) return false

            val hasLeftDyes = columns[0].any { it.item is DyeItem }
            val hasRightDyes = columns.getOrNull(2)?.any { it.item is DyeItem } == true

            return if (thighHighsItem.hasCustomColor(thighHighs)) hasLeftDyes || hasRightDyes else hasLeftDyes && hasRightDyes
        }
    }

    override fun assemble(inv: CraftingInput, lookup: HolderLookup.Provider): ItemStack {
        inv.columns().let { columns ->
            val stack = columns[1].first { it isOf EstrogenItems.ThighHighs }
            val item = stack.item as ThighHighsItem
            val leftDye = columns[0].filter { it.item is DyeItem }
            val rightDye = columns.getOrNull(2)?.filter { it.item is DyeItem }?: listOf()

            var newPrimary: Color? = null
            var newSecondary: Color? = null

            if (item.hasCustomColor(stack)) {
                var oldPrimary = item.getColor(stack, 0)
                var oldSecondary = item.getColor(stack, 1)
                leftDye.forEach { dye ->
                    oldPrimary = mixColorWithDye(oldPrimary, dye)
                }
                rightDye.forEach { dye ->
                    oldSecondary = mixColorWithDye(oldSecondary, dye)
                }
                newPrimary = oldPrimary
                newSecondary = oldSecondary
            } else {
                leftDye.forEach { dye ->
                    newPrimary = if (newPrimary == null) colorFromDye(dye) else {
                        mixColorWithDye(newPrimary, dye)
                    }
                }
                rightDye.forEach { dye ->
                    newSecondary = if (newSecondary == null) colorFromDye(dye) else {
                        mixColorWithDye(newSecondary, dye)
                    }
                }
            }

            val newThighHighs = stack.copy()
            item.setColor(newThighHighs, newPrimary!!, newSecondary!!)
            return newThighHighs
        }
    }

    private fun mixColorWithDye(original: Color, dyeStack: ItemStack): Color {
        if (dyeStack.isEmpty || dyeStack.item !is DyeItem) return original
        val dyeColor: Color = colorFromDye(dyeStack)
        return original.mix(dyeColor, .5f)
    }

    private fun colorFromDye(dyeStack: ItemStack): Color {
        return (dyeStack.item as DyeItem).dyeColor.asColor()
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height == 9
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipeSerializers.THIGH_HIGH_DYE_SERIALIZER.value!!

    private fun CraftingInput.columns(): List<List<ItemStack>> {
        return this.items().withIndex().groupBy { it.index % 3 }.map { entry -> entry.value.map { it.value } }
    }
}