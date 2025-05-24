@file:EventSubscriber
package dev.mayaqq.estrogen.features.boobs

import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.player.PlayerConnectionEvent
import dev.mayaqq.cynosure.utils.currentTime
import dev.mayaqq.estrogen.client.features.boobs.Boob.boobSize
import dev.mayaqq.estrogen.client.features.boobs.Boob.shouldShow
import dev.mayaqq.estrogen.content.EstrogenAttributes

// I love handling boobs

@Subscription
fun onDisconnect(event: PlayerConnectionEvent.Leave) {
    if (shouldShow(event.player)) {
        val startTime: Double = event.player.getAttributeValue(EstrogenAttributes.BoobGrowingStartTime)
        val currentTime: Double = currentTime(event.player.level())
        val size = boobSize(
            startTime, currentTime,
            event.player.getAttributeValue(EstrogenAttributes.BoobInitialSize).toFloat(), 0.0f
        )
        event.player.getAttribute(EstrogenAttributes.BoobInitialSize)?.baseValue = size.toDouble()
    }
}