package dev.mayaqq.estrogen.client.registry;

import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.client.registry.particles.DashParticle;
import dev.mayaqq.estrogen.utils.EstrogenParticleRegistrator;
import dev.mayaqq.estrogen.registry.EstrogenParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.BiConsumer;

public class EstrogenClientEvents {
    public static void onDisconnect() {
        Dash.uwufy = false;
    }

    public static void onRegisterParticles(BiConsumer<ParticleType<SimpleParticleType>, EstrogenParticleRegistrator<SimpleParticleType>> consumer) {
        consumer.accept(EstrogenParticles.DASH.get(), DashParticle.Provider::new);
    }
}
