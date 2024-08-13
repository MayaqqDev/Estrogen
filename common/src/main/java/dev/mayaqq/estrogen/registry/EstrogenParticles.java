package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class EstrogenParticles {
    public static final ResourcefulRegistry<ParticleType<?>> PARTICLES = ResourcefulRegistries.create(BuiltInRegistries.PARTICLE_TYPE, Estrogen.MOD_ID);

    public static final RegistryEntry<SimpleParticleType> DASH = PARTICLES.register("dash", () -> new SimpleParticleType(true) {});
    public static final RegistryEntry<SimpleParticleType> MOTH_FUZZ = PARTICLES.register("moth_fuzz", () -> new SimpleParticleType(true) {});
}
