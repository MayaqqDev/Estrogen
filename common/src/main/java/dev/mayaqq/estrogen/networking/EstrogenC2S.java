package dev.mayaqq.estrogen.networking;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.registry.common.EstrogenSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;

public class EstrogenC2S {

    public static final ResourceLocation DASH = Estrogen.id("dash");
    public static final ResourceLocation DASH_PARTICLES = Estrogen.id("dashparticles");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(DASH, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                    ServerLevel world = player.serverLevel();
                    world.playSound(null, player.blockPosition(), EstrogenSounds.DASH.getMainEvent(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    // summon particles around player
                    world.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 10, 0.5, 0.5, 0.5, 0.5);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver(DASH_PARTICLES, ((server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerLevel world = player.serverLevel();
                //TODO: will add custom particles eventually once coded properly
                world.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
            });
        }));
    }
}