package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClientNetworkManager;

public record DreamBlockSeedPacket(long newSeed) implements Packet<DreamBlockSeedPacket> {

    public static final PacketType<DreamBlockSeedPacket> TYPE = new Type();

    @Override
    public PacketType<DreamBlockSeedPacket> type() {
        return null;
    }

    private static class Type extends CodecPacketType.Client<DreamBlockSeedPacket> {

        public Type() {
            super(DreamBlockSeedPacket.class,
                Estrogen.id("dream_block_seed"),
                ObjectByteCodec.create(
                    ByteCodec.LONG.fieldOf(DreamBlockSeedPacket::newSeed),
                    DreamBlockSeedPacket::new
                )
            );
        }

        @Override
        public Runnable handle(DreamBlockSeedPacket message) {
            return () -> EstrogenClientNetworkManager.handleDreamBlockSeed(message);
        }
    }
}