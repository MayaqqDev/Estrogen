package dev.mayaqq.estrogen.client.registry.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.SpriteSet;

@Environment(value= EnvType.CLIENT)
public class MothFuzzParticle extends CherryParticle {
    public MothFuzzParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet);
    }
}
