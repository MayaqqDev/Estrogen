package dev.mayaqq.estrogen.compat.recipeviewers.api

import dev.mayaqq.estrogen.Estrogen
import net.fabricmc.loader.api.FabricLoader

actual fun getCRVPlugins(): List<PluginContainer> = buildList {
    FabricLoader.getInstance().getEntrypointContainers("crv", CRVPlugin::class.java).forEach {
        try {
            val meta = it.provider.metadata
            add(PluginContainer(it.entrypoint, meta.id))
        } catch(t: Throwable) {
            Estrogen.error("Critical exception thrown when loading Estrogen Module: ${it.provider.metadata.id}", t)
        }
    }
}