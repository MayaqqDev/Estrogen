package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.particles.DashParticle;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {

    public static final DefaultParticleType DASH_PARTICLE = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, Estrogen.id("dash_particle"), DASH_PARTICLE);

    }
}
