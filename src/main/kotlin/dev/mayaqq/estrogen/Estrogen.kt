package dev.mayaqq.estrogen

import dev.mayaqq.estrogen.content.EstrogenBlocks
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uwu.serenity.kritter.RegistryManager

const val MOD_ID = "estrogen"

inline fun modId(path: String) = ResourceLocation(MOD_ID, path)

object Estrogen : Logger by LoggerFactory.getLogger(MOD_ID), RegistryManager by RegistryManager(MOD_ID) {

    fun init() {
        info("Maya is very cute")
        EstrogenBlocks.register()
    }
}