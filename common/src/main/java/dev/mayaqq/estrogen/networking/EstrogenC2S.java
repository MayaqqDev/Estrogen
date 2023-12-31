package dev.mayaqq.estrogen.networking;

import dev.architectury.networking.NetworkManager;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.registry.common.EstrogenSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;

public class EstrogenC2S {

    public static final ResourceLocation DASH = Estrogen.id("dash");
    public static final ResourceLocation DASH_PARTICLES = Estrogen.id("dashparticles");

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, DASH, (buf, context) -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            MinecraftServer server = player.server;
            server.execute(() -> {
                if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
                    ServerLevel world = player.serverLevel();
                    world.playSound(null, player.blockPosition(), EstrogenSounds.DASH.getMainEvent(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    // summon particles around player
                    world.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 10, 0.5, 0.5, 0.5, 0.5);
                }
            });
        });
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, DASH_PARTICLES, ((buf, context) -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            ServerLevel world = player.serverLevel();
            MinecraftServer server = player.server;
            server.execute(() -> {
                world.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
            });
        }));
    }
}