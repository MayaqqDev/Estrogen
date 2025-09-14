package dev.mayaqq.estrogen.forge

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLEnvironment

@Mod(MOD_ID)
class EstrogenForge {
    init {
        if (FMLEnvironment.dist == Dist.CLIENT) EstrogenRenderTypes
        // TODO: Fix this in kritter to allow using eventBus on delegated interface things
//        val delegate = Estrogen::class.java.declaredFields
//            .first { it.type == RegistryManager::class.java }
//            .apply { isAccessible = true }
//            .get(Estrogen) as RegistryManager
//        delegate.eventBus = MOD_BUS
        Estrogen.init()
    }
}