package dev.mayaqq.estrogen.datagen.platform

import com.google.gson.JsonArray
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

interface PlatformHelper {
    val platform: Platform
    fun name(name: String): String
    fun commonTag(name: String): TagKey<Item>
}

interface PlatformRecipeHelper : PlatformHelper {
    fun fluidAmount(amount: Long): Long
    fun isModLoaded(modId: String): EstrogenLoadCondition

    data class EstrogenLoadCondition(val name: String, val condition: JsonArray)
}

enum class Platform {
    FABRIC,
    FORGE,
    COMMON
}