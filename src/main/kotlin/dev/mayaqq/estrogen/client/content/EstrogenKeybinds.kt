package dev.mayaqq.estrogen.client.content

import dev.mayaqq.cynosure.client.events.KeybindRegistrationEvent
import dev.mayaqq.cynosure.events.api.EventSubscriber
import dev.mayaqq.cynosure.events.api.Subscription
import dev.mayaqq.estrogen.Estrogen
import invoke.kitty.kritter.client.keybinds.registerKeyMapping
import invoke.kitty.kritter.platform.Side
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW


object EstrogenKeybinds {
    val DASH_KEY = registerKeyMapping(KeyMapping("key.estrogen.dash", GLFW.GLFW_KEY_X, "category.estrogen"))
}