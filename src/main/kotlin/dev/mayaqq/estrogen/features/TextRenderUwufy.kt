package dev.mayaqq.estrogen.features

import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.estrogen.content.EstrogenTags
import net.minecraft.client.Minecraft

@EventSubscriber(env = [Environment.CLIENT])
object TextRenderUwufy {
    @JvmStatic
    var isEnabled: Boolean = false

    @Subscription
    fun onTick(event: ClientTickEvent.End) {
        val client = Minecraft.getInstance()
        if (client.player == null) {
            disconnect()
        } else if (client.player!!.tickCount % 20 == 0) {
            isEnabled = client.player!!.inventory.contains(EstrogenTags.Items.UWUFYING)
            client.updateTitle()
        }
    }

    fun disconnect() {
        isEnabled = false
    }
}