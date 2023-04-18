package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.entities.DashParticleEntity;
import dev.mayaqq.estrogen.entityRenderers.DashParticleRenderer;
import dev.mayaqq.estrogen.models.DashParticleEntityModel;
import dev.mayaqq.estrogen.networking.S2C;
import dev.mayaqq.estrogen.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class EstrogenClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_DASH_PARTICLE_LAYER = new EntityModelLayer(Estrogen.id("dash_particle"), "main");
    @Override
    public void onInitializeClient() {
        KeybindRegistry.register();
        ClientEventRegistry.register();
        S2C.register();
        FluidRenderRegistry.register();
        PackRegistry.register();

        EntityRendererRegistry.register(EntityRegistry.DASH_PARTICLE_ENTITY, DashParticleRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MODEL_DASH_PARTICLE_LAYER, DashParticleEntityModel::getTexturedModelData);
    }
}
