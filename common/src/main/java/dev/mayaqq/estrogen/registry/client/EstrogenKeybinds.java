package dev.mayaqq.estrogen.registry.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBind;
import org.lwjgl.glfw.GLFW;

public class EstrogenKeybinds {
    public static KeyBind dashKey;
    public static void register() {
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBind(
                "key.estrogen.dash",
                GLFW.GLFW_KEY_X,
                "category.estrogen"
        ));
    }
}
