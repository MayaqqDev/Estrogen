package dev.mayaqq.estrogen.networking;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EffectRegistry;
import dev.mayaqq.estrogen.registry.ParticleRegistry;
import dev.mayaqq.estrogen.registry.SoundRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;

public class C2S {
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(Estrogen.id("dash"), (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player.hasStatusEffect(EffectRegistry.WOMAN_EFFECT)) {
                    ServerWorld world = player.getWorld();
                    world.playSound(null, player.getBlockPos(), SoundRegistry.DASH, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    // summon particles around player
                    world.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 10, 0.5, 0.5, 0.5, 0.5);
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver(Estrogen.id("dashparticles"), ((server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ServerWorld world = player.getWorld();
                //TODO: will add custom particles eventually once coded properly
                world.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
            });
        }));
    }
}