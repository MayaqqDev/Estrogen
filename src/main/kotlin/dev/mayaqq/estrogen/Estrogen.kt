package dev.mayaqq.estrogen

import dev.mayaqq.cynosure.utils.isModLoaded
import dev.mayaqq.estrogen.content.AdvancementTriggers
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenEnchantments
import dev.mayaqq.estrogen.content.EstrogenPotions
import dev.mayaqq.estrogen.content.EstrogenSounds
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectTrigger
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uwu.serenity.kritter.RegistryManager

const val MOD_ID = "estrogen"
const val MOD_NAME = "Estrogen"

private const val mcCapesMessage = """
            ----------------------------------------------------------------------------
            Minecraft Capes is detected! This mod currently causes some features
            of Estrogen to not work properly, before making an issue, please make sure
            to first update and disable Minecraft Capes and see if the issue persists.
            ----------------------------------------------------------------------------
            """

inline fun id(path: String) = ResourceLocation(MOD_ID, path)

object Estrogen : Logger by LoggerFactory.getLogger(MOD_NAME), RegistryManager by RegistryManager(MOD_ID) {

    fun init() {
        if (isModLoaded("minecraftcapes")) {
            mcCapesMessage.split("\n").forEach {
                info("[ESTROGEN] $it")
            }
        }

        EstrogenAttributes.register()
        EstrogenSounds.register()
        EstrogenBlocks.register()
        EstrogenBlockEntities.register()
        EstrogenEffects.register()
        EstrogenEnchantments.register()
        AdvancementTriggers.register()
        EstrogenPotions.register()

        info("Injecting Estrogen into your veins!")

        EstrogenNetwork
    }
}