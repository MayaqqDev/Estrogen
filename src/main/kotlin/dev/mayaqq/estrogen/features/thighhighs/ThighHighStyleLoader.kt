package dev.mayaqq.estrogen.features.thighhighs

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.events.DataPackSyncEvent
import invoke.kitty.kritter.resources.AsyncResourceReloadListener
import invoke.kitty.kritter.serialization.json.GsonObject
import invoke.kitty.kritter.serialization.json.GsonPrimitive
import invoke.kitty.kritter.utils.coroutines.mapAsync
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.withTimeout
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.profiling.ProfilerFiller
import kotlin.jvm.optionals.getOrNull


object ThighHighStyleLoader : AsyncResourceReloadListener<List<ResourceLocation>> {

    init {
        DataPackSyncEvent.subscribe { player, _ ->
            EstrogenItems.ThighHighs.get().syncStyles(player)
        }
    }

    val STYLES_CODEC: Codec<MutableList<ResourceLocation>> = Codec.list(ResourceLocation.CODEC)

    override suspend fun load(resourceManager: ResourceManager, profiler: ProfilerFiller): List<ResourceLocation> = coroutineScope {
        // Reuse semaphore instance bcs were awaiting in between anyways
        val semaphore = Semaphore(16)
        val objects = resourceManager.getResourceStack(id("thigh_high_styles.json"))
            .mapAsync(semaphore) { it.openAsReader().use(JsonParser::parseReader) }

        val filtered = objects
            .filterIsInstance<GsonObject>()
            .takeLastWhile { (it["replace"] as? GsonPrimitive)?.takeIf(GsonPrimitive::isBoolean)?.asBoolean != true }

        filtered.mapAsync(semaphore) { STYLES_CODEC.parse(JsonOps.INSTANCE, it).resultOrPartial().getOrNull() }
            .filterNotNull()
            .flatten()

    }

    override suspend fun apply(data: List<ResourceLocation>, resourceManager: ResourceManager, profiler: ProfilerFiller) {
        EstrogenItems.ThighHighs.get().loadStyles(data)
    }
}