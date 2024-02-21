package dev.mayaqq.estrogen.networking;

import dev.architectury.networking.NetworkManager;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenSounds;
import dev.mayaqq.estrogen.utils.Estromath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class EstrogenC2S {

    public static final ResourceLocation DASH_PARTICLES = Estrogen.id("dashparticles");
    public static final ResourceLocation SPAWN_HEARTS = Estrogen.id("spawnhearts");

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, DASH_PARTICLES, ((buf, context) -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            ServerLevel world = player.serverLevel();
            MinecraftServer server = player.server;
            server.execute(() -> {
                world.sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 1, player.getZ(), 1, 0, 0, 0, 0);
            });
        }));
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SPAWN_HEARTS, ((buf, context) -> {
            ServerPlayer player = (ServerPlayer) context.getPlayer();
            ServerLevel world = player.serverLevel();
            MinecraftServer server = player.server;
            long[] pos = buf.readLongArray();
            double x = Estromath.longToDouble(pos[0]);
            double y = Estromath.longToDouble(pos[1]);
            double z = Estromath.longToDouble(pos[2]);
            BlockPos blockPos = new BlockPos((int) x,(int) y,(int) z);
            ResourceLocation sound = buf.readResourceLocation();
            server.execute(() -> {
                world.sendParticles(ParticleTypes.HEART, x, y, z, 1, 0.5, 0.5, 0.5, 0.5);
                if (!sound.equals(Estrogen.id("empty"))) {
                    SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(sound);
                    world.playSound(null, blockPos, soundEvent, SoundSource.PLAYERS, 1.0F, 10.0F);
                }
            });
        }));
    }
}