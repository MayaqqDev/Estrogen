@file:EventSubscriber
package dev.mayaqq.estrogen.features.thighhighs

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.server.DataPackSyncEvent
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.id
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceManagerReloadListener
import java.util.function.Consumer


object ThighHighStyleLoader : ResourceManagerReloadListener {

    val STYLES_CODEC: Codec<MutableList<ResourceLocation>> = Codec.list(ResourceLocation.CODEC)

    override fun onResourceManagerReload(resourceManager: ResourceManager) {
        val styles: MutableList<ResourceLocation> = ArrayList()
        resourceManager.getResourceStack(id("thigh_high_styles.json"))
            .forEach(Consumer { resource: Resource ->
                try {
                    resource.openAsReader().use { reader ->
                        val root: JsonObject = JsonParser.parseReader(reader).asJsonObject
                        val replaceElement: JsonElement = root.get("replace")

                        val replace = if (replaceElement.isJsonPrimitive) replaceElement.asBoolean else false
                        if (replace) styles.clear()

                        val added: MutableList<ResourceLocation> =
                            STYLES_CODEC.decode(JsonOps.INSTANCE, root.get("values"))
                                .resultOrPartial(Estrogen::error)
                                .map { it.first }
                                .orElseGet { mutableListOf() }
                        styles.addAll(added)
                    }
                } catch (e: Exception) {
                    Estrogen.error("Invalid thigh high styles:", e)
                }
            })
        EstrogenItems.ThighHighs.value!!.loadStyles(styles)
    }
}

@Subscription
fun onDataSync(event: DataPackSyncEvent) {
    if (event.player is ServerPlayer) EstrogenItems.ThighHighs.value!!.syncStyles(event.player as ServerPlayer)
}