package dev.mayaqq.estrogen.modules

import dev.mayaqq.cynosure.core.mod.Mod
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.api.EstrogenModule
import net.fabricmc.loader.api.FabricLoader
import kotlin.jvm.optionals.getOrNull

actual fun getModules(): Set<ModuleContainer> {
    val list = arrayListOf<ModuleContainer>()
    FabricLoader.getInstance().getEntrypointContainers("estrogen", EstrogenModule::class.java).forEach {
        try {
            val meta = it.provider.metadata
            list.add(ModuleContainer(it.entrypoint, Mod(
                meta.id,
                meta.name,
                meta.description,
                meta.version.friendlyString,
                meta.getIconPath(64).getOrNull()
            )))
        } catch(t: Throwable) {
            Estrogen.error("Critical exception thrown when loading Estrogen Module: ${it.provider.metadata.id}", t)
        }
    }
    return list.toSet()
}