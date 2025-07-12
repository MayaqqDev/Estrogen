package dev.mayaqq.estrogen

import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.config.Instance
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.network.EstrogenNetwork
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uwu.serenity.kittyconfig.api.defaults.load
import uwu.serenity.kritter.RegistryManager

const val MOD_ID = "estrogen"
const val MOD_NAME = "Estrogen"

internal inline fun id(path: String) = ResourceLocation(MOD_ID, path)
inline fun mcid(path: String) = ResourceLocation("minecraft", path)

@EventSubscriber
object Estrogen : Logger by LoggerFactory.getLogger(MOD_NAME), RegistryManager by RegistryManager(MOD_ID) {

    fun init() {
        EstrogenCommonConfig.Instance.load()
        EstrogenServerConfig.Instance.load()

        EstrogenAttributes.register()
        EstrogenSounds.register()
        EstrogenBlocks.register()
        EstrogenBlockEntities.register()
        EstrogenEffects.register()
        EstrogenParticles.register()
        EstrogenEnchantments.register()
        AdvancementTriggers.register()
        EstrogenFluids.register()
        EstrogenPotions.register()
        EstrogenItems.register()
        EstrogenCreativeTab.register()
        EstrogenEntities.register()
        EstrogenRecipes.register()
        EstrogenRecipes.Serializers.register()
        EstrogenPoiTypes.register()

        info("Injecting Estrogen into your veins!")

        EstrogenNetwork
    }
}