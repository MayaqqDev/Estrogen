package dev.mayaqq.estrogen.client.features.boobs.data

import invoke.kitty.kritter.resources.JsonDataLoader
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.profiling.ProfilerFiller

object BreastArmorDataLoader : JsonDataLoader<BreastArmorData>(BreastArmorData.serializer(), "estrogen_armor_data") {
    val dataMap = hashMapOf<ResourceLocation, BreastArmorData>()

    override suspend fun apply(data: Map<ResourceLocation, BreastArmorData>, resourceManager: ResourceManager, profilerFiller: ProfilerFiller) {
        dataMap.clear()
        dataMap.putAll(data)
    }

    fun getData(location: ResourceLocation): BreastArmorData? = dataMap[location]
}