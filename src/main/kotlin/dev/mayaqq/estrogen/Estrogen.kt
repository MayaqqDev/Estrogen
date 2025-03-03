package dev.mayaqq.estrogen

import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val MOD_ID = "estrogen"

inline fun modId(path: String) = ResourceLocation(MOD_ID, path)

object Estrogen : Logger by LoggerFactory.getLogger(MOD_ID) {

    fun init() {
        info("Maya is very cute")
    }
}