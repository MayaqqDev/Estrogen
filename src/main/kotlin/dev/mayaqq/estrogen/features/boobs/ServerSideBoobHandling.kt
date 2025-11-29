@file:EventSubscriber
package dev.mayaqq.estrogen.features.boobs

import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.EntityTrackingEvent
import dev.mayaqq.cynosure.events.entity.player.PlayerConnectionEvent
import dev.mayaqq.cynosure.utils.currentTime
import dev.mayaqq.estrogen.client.features.boobs.Boob.boobSize
import dev.mayaqq.estrogen.client.features.boobs.Boob.shouldShow
import dev.mayaqq.estrogen.content.EstrogenAttributes
import dev.mayaqq.estrogen.injection.chestConfig
import dev.mayaqq.estrogen.network.EstrogenNetwork
import dev.mayaqq.estrogen.network.messages.s2c.ChestConfigPacket
import dev.mayaqq.estrogen.network.messages.s2c.ChestConfigRequestPacket
import net.minecraft.world.entity.player.Player

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

@Subscription
fun onEntityTracking(event: EntityTrackingEvent.Start) {
    (event.entity as? Player)?.chestConfig?.let {
        EstrogenNetwork.sendToPlayer(ChestConfigPacket(event.entity.uuid, it), event.player)
    }?: run { EstrogenNetwork.sendToPlayer(ChestConfigRequestPacket(), event.player) }
}

@Subscription
fun onServerJoin(event: PlayerConnectionEvent.Join) {
    EstrogenNetwork.sendToPlayer(ChestConfigRequestPacket(), event.player)
}