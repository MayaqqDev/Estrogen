package dev.mayaqq.estrogen.neoforge

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.common.Mod
import net.neoforged.fml.loading.FMLEnvironment

@Mod(MOD_ID)
object EstrogenNeoForge {
    init {
        if (FMLEnvironment.dist == Dist.CLIENT) EstrogenRenderTypes
        Estrogen.init()
        try {
            //val field = FluidRegistry::class.java.getDeclaredField("registry")
            // field.isAccessible = true
            // (field.get(EstrogenFluids.fluidRegistry) as DeferredRegister<FluidType>).register(MOD_BUS)
        } catch (e: ReflectiveOperationException) {
            Estrogen.error("Failed to initialize fluids for Estrogen: ", e)
        }
    }
}