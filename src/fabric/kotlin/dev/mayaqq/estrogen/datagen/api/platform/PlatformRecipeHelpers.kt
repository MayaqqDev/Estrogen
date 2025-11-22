package dev.mayaqq.estrogen.datagen.api.platform

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.mayaqq.cynosure.utils.tag
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object CommonRecipeHelper: PlatformRecipeHelper {
    override val platform: Platform = Platform.COMMON

    override fun name(name: String): String = "$name (Common)"

    override fun fluidAmount(amount: Long): Long =
        throw UnsupportedOperationException("fluidAmount not supported in common.")

    override fun isModLoaded(modId: String): PlatformRecipeHelper.EstrogenLoadCondition =
        throw UnsupportedOperationException("isModLoaded not supported in common.")

    override fun commonTag(name: String): TagKey<Item> =
        throw UnsupportedOperationException("commonTag not supported in common.")
}

object FabricRecipeHelper: PlatformRecipeHelper {
    override val platform: Platform = Platform.FABRIC

    override fun name(name: String): String = "$name (Fabric)"

    override fun commonTag(name: String): TagKey<Item> = Registries.ITEM.tag(ResourceLocation("c", name))

    override fun fluidAmount(amount: Long): Long = amount

    override fun isModLoaded(modId: String): PlatformRecipeHelper.EstrogenLoadCondition {
        val array = JsonArray()
        val jsonObject = JsonObject()
        jsonObject.addProperty("condition", "fabric:all_mods_loaded")
        val values = JsonArray()
        values.add(modId)
        jsonObject.add("values", values)
        array.add(jsonObject)
        return PlatformRecipeHelper.EstrogenLoadCondition("fabric:load_conditions", array)
    }
}

object ForgeRecipeHelper: PlatformRecipeHelper {
    override val platform: Platform = Platform.FORGE

    override fun name(name: String): String = "$name (Forge)"

    override fun commonTag(name: String): TagKey<Item> = Registries.ITEM.tag(ResourceLocation("forge", name))

    override fun fluidAmount(amount: Long): Long = amount

    override fun isModLoaded(modId: String): PlatformRecipeHelper.EstrogenLoadCondition {
        val conditions = JsonArray()
        val jsonObject = JsonObject()
        jsonObject.addProperty("type", "forge:mod_loaded")
        jsonObject.addProperty("modid", modId)
        conditions.add(jsonObject)
        return PlatformRecipeHelper.EstrogenLoadCondition("conditions", conditions)
    }
}