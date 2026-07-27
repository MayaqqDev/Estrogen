package dev.mayaqq.estrogen.features.thighhighs

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.mayaqq.cynosure.core.VersionHooks.Impl.toKtResult
import dev.mayaqq.cynosure.core.codecs.fieldOf
import dev.mayaqq.cynosure.core.codecs.forGetter
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.events.DataPackSyncEvent
import invoke.kitty.kritter.resources.AsyncResourceReloadListener
import invoke.kitty.kritter.utils.coroutines.mapAsync
import invoke.kitty.kritter.utils.result.flatMap
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.util.profiling.ProfilerFiller


object ThighHighStyleLoader : AsyncResourceReloadListener<List<ThighHighStyleLoader.StyleInstance?>> {

    init {
        DataPackSyncEvent.subscribe { player, _ ->
            EstrogenItems.ThighHighs.get().syncStyles(player)
        }
    }

    data class StyleInstance(val replace: Boolean, val values: List<ResourceLocation>)

    val STYLES_CODEC: Codec<StyleInstance> = RecordCodecBuilder.create { instance -> instance.group(
        Codec.BOOL.optionalFieldOf("replace", false) forGetter StyleInstance::replace,
        ResourceLocation.CODEC.listOf() fieldOf StyleInstance::values
    ).apply(instance, ::StyleInstance) }

    override suspend fun load(resourceManager: ResourceManager, profiler: ProfilerFiller): List<StyleInstance?> = coroutineScope {
        resourceManager.getResourceStack(id("thigh_high_styles.json"))
            .mapAsync(Semaphore(16)) { resource ->
                resource.openAsReader().use { reader ->
                    runCatching { JsonParser.parseReader(reader) }
                        .flatMap { STYLES_CODEC.parse(JsonOps.INSTANCE, it).toKtResult() }
                        .onFailure { Estrogen.error("Error loading thigh high styles", it) }
                        .getOrNull()
                }
            }
    }

    override suspend fun apply(data: List<StyleInstance?>, resourceManager: ResourceManager, profiler: ProfilerFiller) {
        EstrogenItems.ThighHighs.get().loadStyles(
            data.filterNotNull()
                .takeLastWhile { !it.replace }
                .flatMap { it.values }
        )
    }
}