package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.minecraft.diffuseColor
import dev.mayaqq.estrogen.content.EstrogenRecipes
import dev.mayaqq.estrogen.content.items.DreamCatcherItem
import net.minecraft.core.RegistryAccess
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
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
            val dyes: List<ItemStack> = items.filter { it.item is DyeItem }

            if (dyes.size == 3) {
                val new = stack.copyWithCount(1)
                var newLeft: Int
                var newMiddle: Int
                var newRight: Int
                if (item.isBlank(new)) {
                    newLeft = colorFromDye(dyes[0]).toInt()
                    newMiddle = colorFromDye(dyes[1]).toInt()
                    newRight = colorFromDye(dyes[2]).toInt()
                } else {
                    newLeft = mixColorWithDye(Color(item.left(new)!!), dyes[0]).toInt()
                    newMiddle = mixColorWithDye(Color(item.middle(new)!!), dyes[1]).toInt()
                    newRight = mixColorWithDye(Color(item.right(new)!!), dyes[2]).toInt()
                }

                item.setDyes(new, newLeft, newMiddle, newRight)

                return new
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

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height >= 4
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.DREAMCATCHER_DYE_SERIALIZER

}