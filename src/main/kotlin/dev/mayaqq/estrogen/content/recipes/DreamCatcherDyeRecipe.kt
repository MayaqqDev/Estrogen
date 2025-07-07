package dev.mayaqq.estrogen.content.recipes

import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.lighter
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
                val newLeft = item.left(new)?.let {
                    mixColorWithDye(Color(item.left(new)!!), dyes[0])
                } ?: colorFromDye(dyes[0])
                val newMiddle = item.middle(new)?.let {
                    mixColorWithDye(Color(item.middle(new)!!), dyes[1])
                } ?: colorFromDye(dyes[1])
                val newRight = item.right(new)?.let {
                    mixColorWithDye(Color(item.right(new)!!), dyes[2])
                } ?: colorFromDye(dyes[2])

                item.setDyes(new, newLeft.toInt(), newMiddle.toInt(), newRight.toInt())

                return new
            }
        }
        return ItemStack.EMPTY
    }

    private fun mixColorWithDye(original: Color, dyeStack: ItemStack): Color {
        if (dyeStack.isEmpty || dyeStack.item !is DyeItem) return original
        val dyeColor: Color = colorFromDye(dyeStack)
        if (original.toInt() == -1) return dyeColor
        return original.mix(dyeColor, .5f)
    }

    private fun colorFromDye(dyeStack: ItemStack): Color {
        val dyeColors = (dyeStack.item as DyeItem).dyeColor.textureDiffuseColors
        return Color(dyeColors[0], dyeColors[1], dyeColors[2], 1f).lighter()
    }

    override fun canCraftInDimensions(width: Int, height: Int): Boolean = width * height >= 4
    override fun getSerializer(): RecipeSerializer<*> = EstrogenRecipes.Serializers.DREAMCATCHER_DYE_SERIALIZER

}