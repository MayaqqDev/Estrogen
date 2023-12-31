package dev.mayaqq.estrogen.registry;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

import static dev.mayaqq.estrogen.Estrogen.LOGGER;
import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenResourcePacks {
    public static void register() {
        if (!ResourceManagerHelper.registerBuiltinResourcePack(
                id("mayafied_textures"),
                FabricLoader.getInstance().getModContainer("estrogen").orElseThrow(),
                "Mayafied Estrogen",
                ResourcePackActivationType.NORMAL
        )) {
            LOGGER.error("Failed to register Mayafied Estrogen Pack.");
        }
    }
}
