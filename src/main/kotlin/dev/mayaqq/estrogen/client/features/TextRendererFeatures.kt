package dev.mayaqq.estrogen.client.features

import dev.mayaqq.cynosure.client.events.ClientTickEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.platform.Side
import net.minecraft.client.Minecraft

@EventSubscriber(Side.CLIENT)
object TextRendererFeatures {

    @JvmStatic
    var obfuscate: Boolean = false
        private set

    private var uwufyInternal: Boolean = false

    @JvmStatic
    var uwufy: Boolean
        get() = uwufyInternal
        private set(value) {
            uwufyInternal = value
            dirty = true
        }

    private var dirty: Boolean = false

    @Subscription
    fun onTick(event: ClientTickEvent.End) {
        val client = Minecraft.getInstance()
        if (client.player == null) {
            disconnect()
        } else {
            uwufy = client.player!!.inventory.contains(EstrogenTags.Items.UWUFYING)
            obfuscate = client.player!!.hasEffect(EstrogenEffects.Dreaming.holder())
        }
        if (dirty) {
            dirty = false
            client.updateTitle()
        }
    }

    fun disconnect() {
        uwufy = false
        obfuscate = false
    }
}