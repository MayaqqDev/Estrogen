package dev.mayaqq.estrogen

import dev.mayaqq.cynosure.biome.BiomeModifiers
import dev.mayaqq.cynosure.core.Loader
import dev.mayaqq.cynosure.core.currentLoader
import dev.mayaqq.cynosure.data.registerDatapackReloadListener
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.utils.colors.Color
import dev.mayaqq.cynosure.utils.colors.ForestGreen
import dev.mayaqq.cynosure.utils.colors.LightBlue
import dev.mayaqq.cynosure.utils.tag
import dev.mayaqq.estrogen.api.EstrogenEntrypoint
import dev.mayaqq.estrogen.api.EstrogenFlag
import dev.mayaqq.estrogen.api.EstrogenModule
import dev.mayaqq.estrogen.client.content.screen.EstrogenMenuScreen
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.features.thighhighs.ThighHighStyleLoader
import dev.mayaqq.estrogen.network.EstrogenNetwork
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.GenerationStep
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uwu.serenity.kittyconfig.load
import uwu.serenity.kittyconfig.loadConfig
import uwu.serenity.kritter.RegistryManager

const val MOD_ID = "estrogen"
const val MOD_NAME = "Estrogen"

inline fun id(path: String) = ResourceLocation(MOD_ID, path)
inline fun mcid(path: String) = ResourceLocation("minecraft", path)

@EventSubscriber
@EstrogenEntrypoint
object Estrogen : Logger by LoggerFactory.getLogger(MOD_NAME), RegistryManager by RegistryManager(MOD_ID), EstrogenModule {

    fun init() {
        EstrogenCommonConfig.load()
        EstrogenServerConfig.load()

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
        EstrogenFeatures.register()
        EstrogenLootFunctions.register()

        ForestGreen
        BiomeModifiers.addFeature({it.`is`(Registries.BIOME.tag(
            ResourceLocation(if (currentLoader == Loader.FABRIC) "c:climate_cold" else "forge:is_cold/overworld")
        ))},
            GenerationStep.Decoration.SURFACE_STRUCTURES,
            ResourceKey.create(
                Registries.PLACED_FEATURE,
                id("memorial")
            )
        )

        info("Injecting Estrogen into your veins!")

        registerDatapackReloadListener(id("thigh_high_styles"), ThighHighStyleLoader)

        EstrogenNetwork
    }

    override fun createConfigScreen(): (Screen) -> Screen = { EstrogenMenuScreen(it) }
    override val flags: Array<EstrogenFlag> = arrayOf()
    override val color: Color = LightBlue
    override val description: String = "Base Estrogen, contains some recipes + a build-in datapack for vanilla integration."
}