package dev.mayaqq.estrogen.registry.client.registry;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class EstrogenKeybinds {
    public static KeyBinding dashKey;
    public static void register() {
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.estrogen.dash",
                GLFW.GLFW_KEY_X,
                "category.estrogen"
        ));
    }
}
