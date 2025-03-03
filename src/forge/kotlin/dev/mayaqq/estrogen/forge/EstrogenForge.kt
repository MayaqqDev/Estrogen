package dev.mayaqq.estrogen.forge

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import net.minecraftforge.fml.common.Mod

@Mod(MOD_ID)
class EstrogenForge {
    init {
        Estrogen.init()
    }
}