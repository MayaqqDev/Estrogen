@file:EventSubscriber
package dev.mayaqq.estrogen.neoforge.compat

import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.events.entity.MountEvent
import net.minecraftforge.common.util.FakePlayer

@Subscription
fun onMount(event: MountEvent) {
    if (event.entity is FakePlayer) event.cancel()
}

