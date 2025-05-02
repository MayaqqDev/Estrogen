package dev.mayaqq.estrogen.client.features

import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.cynosure.utils.Environment
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenTags
import net.minecraft.client.Minecraft

@EventSubscriber(env = [Environment.CLIENT])
object TextRendererFeatures {

    @JvmStatic
    var obfuscate: Boolean = false
        private set

    @JvmStatic
    var uwufy: Boolean = false
        private set

    @Subscription
    fun onTick(event: ClientTickEvent.End) {
        val client = Minecraft.getInstance()
        if (client.player == null) {
            disconnect()
        } else {
            uwufy = client.player!!.inventory.contains(EstrogenTags.Items.UWUFYING)
            obfuscate = client.player!!.hasEffect(EstrogenEffects.DREAMING)
            client.updateTitle()
        }
    }

    fun disconnect() {
        uwufy = false
        obfuscate = false
    }
}