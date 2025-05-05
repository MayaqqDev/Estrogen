package dev.mayaqq.estrogen.client.features.boobs.data

import com.google.gson.Gson
import com.google.gson.JsonElement
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
import net.minecraft.util.profiling.ProfilerFiller

object BreastArmorDataLoader : SimpleJsonResourceReloadListener(Gson(), "estrogen_armor_data") {
    val data = hashMapOf<ResourceLocation, BreastArmorData>()

    override fun apply(map: Map<ResourceLocation, JsonElement>, resourceManager: ResourceManager, profilerFiller: ProfilerFiller) {
        this.data.clear()
        val new = hashMapOf<ResourceLocation, BreastArmorData>()
        map.forEach { (location, data) ->
            new[location] = BreastArmorData.fromJson(data)
        }
        this.data.putAll(new)
    }

    fun getData(location: ResourceLocation): BreastArmorData? = this.data[location]
}