package dev.mayaqq.estrogen.forge.compat

import dev.mayaqq.estrogen.compat.rei.ReiEstrogenPlugin
import me.shedaniel.rei.api.common.plugins.PluginView
import me.shedaniel.rei.api.common.plugins.REIPlugin
import me.shedaniel.rei.api.common.plugins.REIPluginProvider
import java.lang.String.join
import java.util.Collections

fun registerPlugin() {
    PluginView.getClientInstance().registerPlugin(wrapPlugin(Collections.singletonList("estrogen"), ReiEstrogenPlugin))
}

private fun <P : REIPlugin<*>?> wrapPlugin(modId: List<String>, plugin: REIPluginProvider<P>): REIPluginProvider<P> {
    return object : REIPluginProvider<P> {
        val nameSuffix: String = " [" + join(", ", modId) + "]"

        override fun provide(): Collection<P> {
            return plugin.provide()
        }

        override fun getPluginProviderClass(): Class<P> {
            return plugin.pluginProviderClass
        }

        override fun getPluginProviderName(): String {
            val var10000 = plugin.pluginProviderName
            return var10000 + this.nameSuffix
        }
    }
}
