package dev.mayaqq.estrogen.registry.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

@Environment(EnvType.CLIENT)
public class DashParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    DashParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider; //Sets the sprite provider from above to the sprite provider in the constructor parameters
        this.maxAge = 200; //200 ticks = 10 seconds
        this.scale = 0.1f;
        this.velocityX = velX; //The velX from the constructor parameters
        this.velocityY = -0.07f; //Allows the particle to slowly fall
        this.velocityZ = velZ;
        this.x = x; //The x from the constructor parameters
        this.y = y;
        this.z = z;
        this.collidesWithWorld = true;
        this.alpha = 1.0f; //Setting the alpha to 1.0f means there will be no opacity change until the alpha value is changed
        this.setSpriteForAge(spriteProvider); //Required
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.prevAngle = this.angle; //required for rotating the particle
        if(this.age++ >= this.maxAge || this.scale <= 0) { //Despawns the particle if the age has reached the max age, or if the scale is 0
            this.markDead(); //Despawns the particle
        } else {
            this.setSpriteForAge(this.spriteProvider); //Animates the particle if needed
            this.move(this.velocityX, this.velocityY, this.velocityZ);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT; //Allows for the texture to have some transparency
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        //The factory used in a particle's registry
        private final SpriteProvider spriteProvider;
        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ) {
            return new DashParticle(clientWorld, x, y, z, velX, velY, velZ, this.spriteProvider);
        }
    }
}
