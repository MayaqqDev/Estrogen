package dev.mayaqq.estrogen.client.content

import invoke.kitty.kritter.client.keybinds.registerKeyMapping
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW


object EstrogenKeybinds {
    val DASH_KEY = registerKeyMapping(KeyMapping("key.estrogen.dash", GLFW.GLFW_KEY_X, "category.estrogen"))
}