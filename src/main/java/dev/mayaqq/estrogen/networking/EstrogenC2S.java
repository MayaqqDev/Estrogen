package dev.mayaqq.estrogen.networking;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.registry.common.EstrogenSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;

public class EstrogenC2S {

    public static final Identifier DASH = Estrogen.id("dash");
    public static final Identifier DASH_PARTICLES = Estrogen.id("dashparticles");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(DASH, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                    ServerWorld world = player.getServerWorld();
                    world.playSound(null, player.getBlockPos(), EstrogenSounds.DASH, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    // summon particles around player
                    world.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 10, 0.5, 0.5, 0.5, 0.5);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver(DASH_PARTICLES, ((server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld world = player.getServerWorld();
                //TODO: will add custom particles eventually once coded properly
                world.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
            });
        }));
    }
}