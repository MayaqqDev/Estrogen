package dev.mayaqq.estrogen.client.content

import dev.mayaqq.cynosure.client.keymapping.register
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

object EstrogenKeybinds {
    val DASH_KEY = KeyMapping("key.estrogen.dash", GLFW.GLFW_KEY_X, "category.estrogen").register()
}