package dev.mayaqq.estrogen.networking;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.entities.DashParticleEntity;
import dev.mayaqq.estrogen.registry.EffectRegistry;
import dev.mayaqq.estrogen.registry.EntityRegistry;
import dev.mayaqq.estrogen.registry.SoundRegistry;
import dev.mayaqq.estrogen.utils.Multithreading;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;

import java.util.concurrent.TimeUnit;

import static dev.mayaqq.estrogen.Estrogen.LOGGER;

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
                Entity entity = new DashParticleEntity(EntityRegistry.DASH_PARTICLE_ENTITY, world);
                entity.setPos(player.getX(), player.getY(), player.getZ());
                entity.setVelocity(0, 0, 0);
                entity.setNoGravity(true);
                entity.setYaw(player.getYaw());
                entity.setPitch(player.getPitch());
                world.spawnEntity(entity);
            });
        }));
    }
}