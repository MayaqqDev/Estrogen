package dev.mayaqq.estrogen.client.registry;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class EstrogenKeybinds {
    public static KeyMapping dashKey;

    public static void register() {
        dashKey = new KeyMapping(
                "key.estrogen.dash",
                GLFW.GLFW_KEY_X,
                "category.estrogen"
        );
        KeyMappingRegistry.register(dashKey);
    }
}
