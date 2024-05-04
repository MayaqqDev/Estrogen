package dev.mayaqq.estrogen.utils;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;

@FunctionalInterface
public interface EstrogenParticleRegistrator<T extends ParticleOptions> {
    ParticleProvider<T> create(SpriteSet spriteSet);
}
