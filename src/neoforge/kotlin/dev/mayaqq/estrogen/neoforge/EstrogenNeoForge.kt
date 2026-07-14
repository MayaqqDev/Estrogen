package dev.mayaqq.estrogen.neoforge

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.pipeline.TextureTarget
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.neoforge.client.EstrogenForgeClient
import invoke.kitty.kritter.platform.Mod
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.platform.forge.eventBus
import invoke.kitty.kritter.utils.clientOnly
import net.minecraft.client.Minecraft

object EstrogenNeoForge {
    @EntrypointHandler("init")
    fun init(mod: Mod) {
        Minecraft.getInstance().mainRenderTarget
        clientOnly {
            mod.eventBus.register(EstrogenForgeClient)
            EstrogenRenderTypes
        }
        try {
            //val field = FluidRegistry::class.java.getDeclaredField("registry")
            // field.isAccessible = true
            // (field.get(EstrogenFluids.fluidRegistry) as DeferredRegister<FluidType>).register(MOD_BUS)
        } catch (e: ReflectiveOperationException) {
            Estrogen.error("Failed to initialize fluids for Estrogen: ", e)
        }
    }
}