package dev.mayaqq.estrogen.forge

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.EstrogenRenderTypes
import dev.mayaqq.estrogen.content.EstrogenFluids
import earth.terrarium.botarium.common.registry.fluid.FluidRegistry
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fluids.FluidType
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(MOD_ID)
object EstrogenForge {
    init {
        if (FMLEnvironment.dist == Dist.CLIENT) EstrogenRenderTypes
        // TODO: Fix this in kritter to allow using eventBus on delegated interface things
//        val delegate = Estrogen::class.java.declaredFields
//            .first { it.type == RegistryManager::class.java }
//            .apply { isAccessible = true }
//            .get(Estrogen) as RegistryManager
//        delegate.eventBus = MOD_BUS
        Estrogen.init()
        try {
            val field = FluidRegistry::class.java.getDeclaredField("registry")
            field.isAccessible = true
            (field.get(EstrogenFluids.fluidRegistry) as DeferredRegister<FluidType>).register(MOD_BUS)
        } catch (e: ReflectiveOperationException) {
            Estrogen.error("Failed to initialize fluids for Estrogen: ", e)
        }
    }
}