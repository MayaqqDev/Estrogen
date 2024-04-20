package dev.mayaqq.estrogen.networking.messages.c2s;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("ClassEscapesDefinedScope")
public record SpawnHeartsPacket(Vec3 pos, ResourceLocation ambientSound) implements Packet<SpawnHeartsPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("spawnhearts");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<SpawnHeartsPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<SpawnHeartsPacket> {

        @Override
        public void encode(SpawnHeartsPacket message, FriendlyByteBuf buffer) {
            buffer.writeDouble(message.pos.x);
            buffer.writeDouble(message.pos.y);
            buffer.writeDouble(message.pos.z);
            buffer.writeResourceLocation(message.ambientSound);
        }

        @Override
        public SpawnHeartsPacket decode(FriendlyByteBuf buffer) {
            return new SpawnHeartsPacket(new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()), buffer.readResourceLocation());
        }

        @Override
        public PacketContext handle(SpawnHeartsPacket message) {
            return (player, level) -> {
                ServerLevel serverLevel = (ServerLevel) level;
                ResourceLocation sound = message.ambientSound;
                serverLevel.sendParticles(ParticleTypes.HEART, message.pos.x, message.pos.y, message.pos.z, 1, 0.5, 0.5, 0.5, 0.5);
                if (!sound.equals(Estrogen.id("empty"))) {
                    BlockPos blockPos = new BlockPos((int) message.pos.x, (int) message.pos.y, (int) message.pos.z);
                    SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(sound);
                    serverLevel.playSound(null, blockPos, soundEvent, SoundSource.PLAYERS, 1.0F, 10.0F);
                    player.swing(InteractionHand.MAIN_HAND, true);
                }
            };
        }
    }
}