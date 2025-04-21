package dev.mayaqq.estrogen.client.content

import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

object EstrogenKeybinds {
    //TODO: Register keymapping using cynosure
    val DASH_KEY = KeyMapping("key.estrogen.dash", GLFW.GLFW_KEY_X, "category.estrogen")
}