package dev.mayaqq.estrogen

import dev.mayaqq.estrogen.content.EstrogenBlockEntities
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenSounds
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
        info("Maya is very cute")
        EstrogenSounds.register()
        EstrogenBlocks.register()
        EstrogenBlockEntities.register()
    }
}