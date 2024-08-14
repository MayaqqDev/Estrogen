package dev.mayaqq.estrogen.networking.messages.c2s;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.function.Consumer;

public record SpawnHeartsPacket(Vector3f pos, ResourceLocation ambientSound) implements Packet<SpawnHeartsPacket> {

    public static final ServerboundPacketType<SpawnHeartsPacket> TYPE = new Type();

    public SpawnHeartsPacket(Vec3 pos, ResourceLocation ambientSound) {
        this(pos.toVector3f(), ambientSound);
    }

    @Override
    public PacketType<SpawnHeartsPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType.Server<SpawnHeartsPacket> {

        public Type() {
            super(
                    SpawnHeartsPacket.class,
                    Estrogen.id("spawnhearts"),
                    ObjectByteCodec.create(
                            ExtraByteCodecs.VECTOR_3F.fieldOf(SpawnHeartsPacket::pos),
                            ExtraByteCodecs.RESOURCE_LOCATION.fieldOf(SpawnHeartsPacket::ambientSound),
                            SpawnHeartsPacket::new
                    )
            );
        }

        @Override
        public Consumer<Player> handle(SpawnHeartsPacket message) {
            return (player) -> {
                ServerLevel serverLevel = (ServerLevel) player.level();
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