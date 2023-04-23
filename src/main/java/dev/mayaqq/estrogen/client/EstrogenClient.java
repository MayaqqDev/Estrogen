package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.networking.S2C;
import dev.mayaqq.estrogen.registry.ParticleRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.screen.PlayerScreenHandler;

public class EstrogenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeybindRegistry.register();
        ClientEventRegistry.register();
        S2C.register();
        FluidRenderRegistry.register();
        PackRegistry.register();
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(Estrogen.id("particle/dash_particle"));
        }));

        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.DASH_PARTICLE, FlameParticle.Factory::new);

    }
}
