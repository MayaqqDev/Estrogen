@file:Suppress("NOTHING_TO_INLINE")
package dev.mayaqq.estrogen

import dev.mayaqq.cynosure.biome.BiomeModifiers
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.data.registerDatapackReloadListener
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.utils.contains
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
import invoke.kitty.kritter.utils.color.Color
import invoke.kitty.kritter.utils.color.ForestGreen
import invoke.kitty.kritter.utils.color.LightBlue
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.GenerationStep
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val MOD_ID = "estrogen"
const val MOD_NAME = "Estrogen"

// id utility
inline fun id(path: String) = identifier(MOD_ID, path)
inline fun mcid(path: String) = identifier("minecraft", path)
inline fun cid(path: String) = identifier("c", path)

@EventSubscriber
@EstrogenEntrypoint
object Estrogen : Logger by LoggerFactory.getLogger(MOD_NAME), EstrogenModule {

    fun init() {
        // Config
        EstrogenCommonConfig.initialize()
        EstrogenServerConfig.initialize()
        //Registries
        EstrogenAttributes.register()
        EstrogenSounds.register()
        EstrogenComponents.register()
        EstrogenBlocks.register()
        EstrogenBlockEntities.register()
        EstrogenEffects.register()
        EstrogenParticles.register()
        EstrogenEnchantments.register()
        AdvancementTriggers.register()
        EstrogenFluids.register()
        EstrogenFluids.fluidRegistry.init()
        EstrogenPotions.register()
        EstrogenRecordSongs.register()
        EstrogenItems.register()
        EstrogenCreativeTab.register()
        EstrogenEntities.register()
        EstrogenRecipes.register()
        EstrogenRecipes.Serializers.register()
        EstrogenPoiTypes.register()
        EstrogenFeatures.register()
        EstrogenLootFunctions.register()

        // Register Packets
        EstrogenNetwork
        // Reload Listeners
        registerDatapackReloadListener(id("thigh_high_styles"), ThighHighStyleLoader)
        // Forest Green
        ForestGreen
        // Biome Modifiers
        BiomeModifiers.addFeature({
            it in Registries.BIOME.tag(cid("is_cold/overworld")) && it in Registries.BIOME.tag(cid("is_mountain"))
          },
            GenerationStep.Decoration.SURFACE_STRUCTURES,
            ResourceKey.create(Registries.PLACED_FEATURE, id("memorial"))
        )

        info("Injecting Estrogen into your veins!")
    }

    // Estrogen Module Info stuff
    override fun createConfigScreen(): (Screen) -> Screen = { EstrogenMenuScreen(it) }
    override val flags: Array<EstrogenFlag> = arrayOf()
    override val color: Color = LightBlue
    override val description: String = "Base Estrogen, contains some recipes + a build-in datapack for vanilla integration."
}