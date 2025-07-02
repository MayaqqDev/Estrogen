package dev.mayaqq.estrogen.content.recipes

import net.minecraft.client.Minecraft

object CreativelessShapedRecipeClient {
    fun isInCreative(): Boolean = Minecraft.getInstance().player?.isCreative == true
}