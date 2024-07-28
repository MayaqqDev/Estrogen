package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClientNetworkManager;
import dev.mayaqq.estrogen.config.ChestConfig;

import java.util.UUID;

public record ChestConfigPacket(UUID uuid, ChestConfig config) implements Packet<ChestConfigPacket> {

    public static ClientboundPacketType<ChestConfigPacket> TYPE = new Type();

    @Override
    public PacketType<ChestConfigPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType.Client<ChestConfigPacket> {

        public Type() {
            super(
                    ChestConfigPacket.class,
                    Estrogen.id("player_chest_config"),
                    ObjectByteCodec.create(
                            ByteCodec.UUID.fieldOf(ChestConfigPacket::uuid),
                            ChestConfig.BYTE_CODEC.fieldOf(ChestConfigPacket::config),
                            ChestConfigPacket::new
                    )
            );
        }

        @Override
        public Runnable handle(ChestConfigPacket message) {
            return () -> EstrogenClientNetworkManager.handleChestConfig(message);
        }
    }
}
