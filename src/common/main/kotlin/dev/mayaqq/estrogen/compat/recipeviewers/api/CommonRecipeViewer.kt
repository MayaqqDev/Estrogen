package dev.mayaqq.estrogen.compat.recipeviewers.api

object CommonRecipeViewer {

    val collectedPlugins: MutableList<PluginContainer> = mutableListOf()

    fun getPlugins(): List<PluginContainer> {
        if (collectedPlugins.isEmpty()) collectedPlugins.addAll(getCRVPlugins())
        return collectedPlugins
    }
}

data class PluginContainer(val plugin: CRVPlugin, val modid: String)

expect fun getCRVPlugins(): List<PluginContainer>