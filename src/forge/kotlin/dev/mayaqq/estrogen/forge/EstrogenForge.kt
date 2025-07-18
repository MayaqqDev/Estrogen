package dev.mayaqq.estrogen.forge

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import net.minecraftforge.fml.common.Mod

@Mod(MOD_ID)
class EstrogenForge {
    init {
        EstrogenRenderTypes
        Estrogen.init()
    }
}