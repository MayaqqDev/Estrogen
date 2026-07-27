package dev.mayaqq.estrogen.neoforge

import dev.mayaqq.estrogen.content.EstrogenFluids
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.platform.forge.eventBus
import net.neoforged.neoforge.registries.DeferredRegister

object EstrogenNeoForge {
    @EntrypointHandler("init")
    fun init(mod: Mod) {
        // ResourcefulLib's no-argument init() uses ModLoadingContext, but
        // Kritter initializes before NeoForge has populated that global context.
        // Bind the library's DeferredRegister to Estrogen's actual bus instead.
        val registryField = EstrogenFluids.fluidRegistry.javaClass.getDeclaredField("registry")
        registryField.trySetAccessible()
        val registry = registryField.get(EstrogenFluids.fluidRegistry) as DeferredRegister<*>
        registry.register(mod.eventBus)
    }
}
