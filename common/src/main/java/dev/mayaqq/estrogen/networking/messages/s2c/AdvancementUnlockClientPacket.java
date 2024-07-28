package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClientNetworkManager;
import net.minecraft.resources.ResourceLocation;

public record AdvancementUnlockClientPacket(ResourceLocation advancement) implements Packet<AdvancementUnlockClientPacket> {
    public static ClientboundPacketType<AdvancementUnlockClientPacket> TYPE = new AdvancementUnlockClientPacket.Type();

    @Override
    public PacketType<AdvancementUnlockClientPacket> type() {
        return null;
    }


    private static class Type extends CodecPacketType.Client<AdvancementUnlockClientPacket> {
        public Type() {
            super(AdvancementUnlockClientPacket.class, Estrogen.id("advancement_unlock_client"), ObjectByteCodec.create(
                    ExtraByteCodecs.RESOURCE_LOCATION.fieldOf(AdvancementUnlockClientPacket::advancement),
                    AdvancementUnlockClientPacket::new
            ));
        }

        @Override
        public Runnable handle(AdvancementUnlockClientPacket message) {
            return () -> EstrogenClientNetworkManager.handleAdvancementUnlockClient(message);
        }
    }
}
