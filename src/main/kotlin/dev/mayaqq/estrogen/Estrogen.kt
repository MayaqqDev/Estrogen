@file:Suppress("NOTHING_TO_INLINE")
package dev.mayaqq.estrogen

import dev.mayaqq.cynosure.biome.BiomeModifiers
import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.MainBus
import dev.mayaqq.cynosure.utils.contains
import dev.mayaqq.cynosure.utils.tag
import dev.mayaqq.estrogen.client.hookClientEventBus
import dev.mayaqq.estrogen.compat.cobblemon.ModlessCobblemonCompat
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.config.EstrogenServerConfig
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectEvents
import dev.mayaqq.estrogen.content.blocks.CauldronInteractions
import dev.mayaqq.estrogen.content.blocks.DreamBlock
import dev.mayaqq.estrogen.content.effects.EstrogenEffect
import dev.mayaqq.estrogen.content.recipes.EntityInteractionRecipeEvents
import dev.mayaqq.estrogen.features.boobs.ServerSideBoobHandling
import dev.mayaqq.estrogen.features.extra.BoobPeople
import dev.mayaqq.estrogen.features.minigame.Minigame
import dev.mayaqq.estrogen.features.thighhighs.ThighHighStyleLoader
import dev.mayaqq.estrogen.features.thighhighs.ThighHighStyleLootFunction
import dev.mayaqq.estrogen.network.EstrogenNetwork
import invoke.kitty.kritter.platform.forge.EntrypointHandler
import invoke.kitty.kritter.resources.registerReloadListener
import invoke.kitty.kritter.utils.clientOnly
import invoke.kitty.kritter.utils.color.ForestGreen
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.packs.PackType
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
object Estrogen : Logger by LoggerFactory.getLogger(MOD_NAME) {

    @EntrypointHandler("init")
    fun init() {
        hookEventBus()
        clientOnly { hookClientEventBus() }
        // Config
        EstrogenCommonConfig.initialize()
        EstrogenServerConfig.initialize()
        //Registries
        EstrogenAttributes.register()
        EstrogenSounds.register()
        EstrogenComponents.register()
        EstrogenBlocks.register()
        EstrogenBlockEntities.register()
        EstrogenParticles.register()
        EstrogenEffects.register()
        AdvancementTriggers.register()
        EstrogenFluids.register()
        EstrogenPotions.register()
        EstrogenItems.register()
        EstrogenCreativeTab.register()
        EstrogenEntities.register()
        EstrogenRecipes.register()
        EstrogenRecipeSerializers.register()
        EstrogenPoiTypes.register()
        EstrogenFeatures.register()
        EstrogenLootFunctions.register()

        // Register Packets
        EstrogenNetwork.initialize()
        // Reload Listeners
        registerReloadListener(PackType.SERVER_DATA, id("thigh_high_styles"), ThighHighStyleLoader)
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

    fun hookEventBus() {
        listOf(
            Estrogen,
            ModlessCobblemonCompat,
            EstrogenAttributeEvents,
            KilledWithEffectEvents,
            CauldronInteractions,
            DreamBlock,
            EstrogenEffect,
            EntityInteractionRecipeEvents,
            ServerSideBoobHandling,
            BoobPeople,
            Minigame,
            ThighHighStyleLootFunction
        ).forEach(MainBus::subscribe)
    }
}