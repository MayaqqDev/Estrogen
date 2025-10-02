package dev.mayaqq.estrogen.modules

import dev.mayaqq.estrogen.api.EstrogenFlag

expect fun getModules(): Set<ModuleContainer>

internal fun anyModuleHasFlag(flag: EstrogenFlag): Boolean = getModules().any { it.hasFlag(flag) }

val moduleCount
    get() = getModules().size