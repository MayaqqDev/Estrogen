@file:EventSubscriber
package dev.mayaqq.estrogen.features.extra

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.PlayerConnectionEvent
import dev.mayaqq.cynosure.utils.currentTime
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.client.features.boobs.Boob
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.content.items.GenderChangePotionItem
import invoke.kitty.kritter.registry.api.entry.holder
import net.minecraft.world.entity.player.Player
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*


const val url = "https://mayaqq.dev/files/boob_people.json"
val boobPeople = ArrayList<UUID>()

object BoobPeople {
    @Subscription
    fun onPlayerJoin(event: PlayerConnectionEvent.Join) {
        if (!event.player.tags.contains("estrogen_first_join")) {
            if (isBoobPerson(event.player)) {
                GenderChangePotionItem.changeGender(event.player.level(), event.player, 1)
            }
            event.player.addTag("estrogen_first_join")
        }

        if (Boob.shouldShow(event.player)) event.player.getAttribute(EstrogenAttributes.BoobGrowingStartTime.holder)?.baseValue = currentTime(event.player.level());
    }

}

private fun isBoobPerson(player: Player): Boolean {
    return getBoobPeople(player).contains(player.uuid)
}

fun getBoobPeople(player: Player): ArrayList<UUID> {
    if (boobPeople.isEmpty()) {
        val client = HttpClient.newHttpClient()
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()
        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse<String>::body)
                .thenAccept { body ->
                    val jsonArray: JsonArray = JsonParser.parseString(body).asJsonArray
                    jsonArray.forEach { jsonElement -> boobPeople.add(UUID.fromString(jsonElement.asString)) }
                    if (boobPeople.contains(player.uuid)) {
                        GenderChangePotionItem.changeGender(player.level(), player, 1)
                    }
                }
        } catch (e: Exception) {
            Estrogen.error("Failed to fetch boob json: ", e)
        }
    }
    return boobPeople
}