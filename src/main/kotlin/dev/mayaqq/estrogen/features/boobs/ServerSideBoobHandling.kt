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
import dev.mayaqq.estrogen.utils.holder
import net.minecraft.world.entity.player.Player
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.toKotlinUuid

// I love handling boobs

@Subscription
fun onDisconnect(event: PlayerConnectionEvent.Leave) {
    if (shouldShow(event.player)) {
        val startTime: Double = event.player.getAttributeValue(EstrogenAttributes.BoobGrowingStartTime.holder())
        val currentTime: Double = currentTime(event.player.level())
        val size = boobSize(
            startTime, currentTime,
            event.player.getAttributeValue(EstrogenAttributes.BoobInitialSize.holder()).toFloat(), 0.0f
        )
        event.player.getAttribute(EstrogenAttributes.BoobInitialSize.holder())?.baseValue = size.toDouble()
    }
}

@Subscription
@OptIn(ExperimentalUuidApi::class)
fun onEntityTracking(event: EntityTrackingEvent.Start) {
    (event.entity as? Player)?.chestConfig?.let {
        EstrogenNetwork.sendToPlayer(event.player, ChestConfigPacket(event.entity.uuid.toKotlinUuid(), it))
    }?: run { EstrogenNetwork.sendToPlayer(event.player, ChestConfigRequestPacket()) }
}

@Subscription
fun onServerJoin(event: PlayerConnectionEvent.Join) {
    EstrogenNetwork.sendToPlayer(event.player, ChestConfigRequestPacket())
}