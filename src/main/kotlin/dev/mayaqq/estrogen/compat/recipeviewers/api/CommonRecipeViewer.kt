package dev.mayaqq.estrogen.compat.recipeviewers.api

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.compat.recipeviewers.EstrogenRecipeViewerPlugin

object CommonRecipeViewer {
    fun getPlugins(): List<PluginContainer> {
        return listOf(PluginContainer(EstrogenRecipeViewerPlugin, MOD_ID))
    }
}

data class PluginContainer(val plugin: CRVPlugin, val modid: String)