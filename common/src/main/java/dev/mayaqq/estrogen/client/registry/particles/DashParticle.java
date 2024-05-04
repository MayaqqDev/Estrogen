package dev.mayaqq.estrogen.client.registry.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class DashParticle extends TextureSheetParticle {

    public DashParticle(ClientLevel level, double x, double y, double z) {
        this(level, x, y, z, 0, 0, 0);
    }

    public DashParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DashParticle dashParticle = new DashParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            dashParticle.pickSprite(sprites);
            return dashParticle;
        }
    }
}
