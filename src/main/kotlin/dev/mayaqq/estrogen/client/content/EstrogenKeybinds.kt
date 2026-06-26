package dev.mayaqq.estrogen.client.content

import dev.mayaqq.cynosure.client.events.KeybindRegistrationEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import invoke.kitty.kritter.platform.Side
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

@EventSubscriber(Side.CLIENT)
object EstrogenKeybinds {
    val DASH_KEY = KeyMapping("key.estrogen.dash", GLFW.GLFW_KEY_X, "category.estrogen")

    @Subscription
    fun onKeybindsEvent(event: KeybindRegistrationEvent) {
        event.register(DASH_KEY)
    }
}