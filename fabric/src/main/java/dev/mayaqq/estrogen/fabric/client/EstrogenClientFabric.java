package dev.mayaqq.estrogen.fabric.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.world.inventory.InventoryMenu;

import java.util.List;

public class EstrogenClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // init Estrogen Client on fabric
        List<String> textures = List.of(
                "blank_lava/blank_lava",
                "liquid_estrogen/liquid_estrogen"
        );
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            for (String tex : textures) {
                registry.register(Estrogen.id("block/" + tex + "_still"));
                registry.register(Estrogen.id("block/" + tex + "_flow"));
            }
        }));
        EstrogenClient.init();
    }
}
