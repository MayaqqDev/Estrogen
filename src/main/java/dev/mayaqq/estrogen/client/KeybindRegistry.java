package dev.mayaqq.estrogen.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeybindRegistry {
    public static KeyBinding dashKey;
    public static void register() {
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.estrogen.dash",
                GLFW.GLFW_KEY_X,
                "category.estrogen"
        ));
    }
}
