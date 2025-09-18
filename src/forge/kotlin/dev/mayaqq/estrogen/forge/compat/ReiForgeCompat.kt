package dev.mayaqq.estrogen.forge.compat

import dev.mayaqq.estrogen.compat.recipeviewers.rei.ReiEstrogenPlugin
import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.common.plugins.PluginView
import me.shedaniel.rei.api.common.plugins.REIPluginProvider

/*
 This is a fucked up way to go around REI only having the annotation on the forge side... (Fuck you REI)
 */
fun registerPlugin() {
    val plugin = ReiEstrogenPlugin()
    PluginView.getClientInstance().registerPlugin(object : REIPluginProvider<REIClientPlugin> {
        override fun provide(): Collection<REIClientPlugin> = plugin.provide()
        override fun getPluginProviderClass(): Class<REIClientPlugin> = plugin.pluginProviderClass
        override fun getPluginProviderName(): String = plugin.pluginProviderName + " [estrogen]"
    })
}
