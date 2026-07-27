package dev.mayaqq.estrogen.neoforge

import dev.mayaqq.estrogen.content.EstrogenFluids
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.platform.forge.eventBus
import net.neoforged.neoforge.fluids.FluidType
import net.neoforged.neoforge.registries.DeferredRegister

object EstrogenNeoForge {
    @EntrypointHandler("init")
    fun init(mod: Mod) {
        // Same workaround as before, actual type is NeoforgeResourcefulFluidRegistry
        EstrogenFluids.fluidRegistry::class.java.getDeclaredField("registry")
            .apply { trySetAccessible() }
            .let { field ->
                field.get(EstrogenFluids.fluidRegistry) as DeferredRegister<FluidType>
            }.register(mod.eventBus)
    }
}