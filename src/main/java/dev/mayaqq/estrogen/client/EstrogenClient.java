package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenFluidRender;
import dev.mayaqq.estrogen.client.registry.EstrogenKeybinds;
import dev.mayaqq.estrogen.client.registry.EstrogenRenderer;
import dev.mayaqq.estrogen.client.registry.EstrogenResourcePacks;
import dev.mayaqq.estrogen.integrations.EarsCompat;
import dev.mayaqq.estrogen.networking.EstrogenS2C;
import dev.mayaqq.estrogen.registry.EstrogenParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.screen.PlayerScreenHandler;

public class EstrogenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EstrogenKeybinds.register();
        Dash.register();
        EstrogenS2C.register();
        EstrogenFluidRender.register();
        EstrogenResourcePacks.register();
        EstrogenRenderer.register();
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(Estrogen.id("particle/dash_particle"));
        }));

        ParticleFactoryRegistry.getInstance().register(EstrogenParticles.DASH_PARTICLE, FlameParticle.Factory::new);

        if (FabricLoader.getInstance().isModLoaded("ears")) {
            EarsCompat.boob();
        }

    }
}
