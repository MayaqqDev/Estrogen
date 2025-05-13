package dev.mayaqq.estrogen.content.recipes.viewers

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

interface RecipeViewerInfo {
    val display: ItemStack
    val catalyst: ResourceLocation
}