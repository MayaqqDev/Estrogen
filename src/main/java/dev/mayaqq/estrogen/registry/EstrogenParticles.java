package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class EstrogenParticles {

    public static final DefaultParticleType DASH_PARTICLE = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, Estrogen.id("dash_particle"), DASH_PARTICLE);

    }
}
