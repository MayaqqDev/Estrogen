package dev.mayaqq.estrogen.modules

import dev.mayaqq.cynosure.core.mod.Mod
import dev.mayaqq.estrogen.api.EstrogenModule

data class ModuleContainer(
    val module: EstrogenModule,
    val mod: Mod,
    val modid: String = mod.modid,
    val modname: String = mod.name
)
