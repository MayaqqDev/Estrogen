package dev.mayaqq.estrogen.forge

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.nyon.klf.MOD_BUS
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLEnvironment
import uwu.serenity.kritter.eventBus

@Mod(MOD_ID)
class EstrogenForge {
    init {
        if (FMLEnvironment.dist == Dist.CLIENT) EstrogenRenderTypes
        Estrogen.eventBus = MOD_BUS
        Estrogen.init()
    }
}