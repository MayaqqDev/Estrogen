package dev.mayaqq.estrogen.client.registry;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("SameParameterValue")
public class EstrogenKeybinds {
    public static final KeyMapping DASH_KEY = new KeyMapping("key.estrogen.dash", GLFW.GLFW_KEY_X, "category.estrogen");

    public static void register() {
        addCategory("category.estrogen");
    }

    private static void addCategory(String categoryTranslationKey) {
        Map<String, Integer> map = KeyMapping.CATEGORY_SORT_ORDER;

        if (map.containsKey(categoryTranslationKey)) {
            return;
        }

        Optional<Integer> largest = map.values().stream().max(Integer::compareTo);
        int largestInt = largest.orElse(0);
        map.put(categoryTranslationKey, largestInt + 1);
    }
}
