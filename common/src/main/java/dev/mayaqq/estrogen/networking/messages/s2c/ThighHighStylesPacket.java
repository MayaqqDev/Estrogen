package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ThighHighStylesPacket(List<ResourceLocation> styles) implements Packet<ThighHighStylesPacket> {

    public static final ClientboundPacketType<ThighHighStylesPacket> TYPE = new Type();

    @Override
    public PacketType<ThighHighStylesPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType.Client<ThighHighStylesPacket> {

        public Type() {
            super(
                ThighHighStylesPacket.class,
                Estrogen.id("thigh_high_styles"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.RESOURCE_LOCATION.listOf().fieldOf(ThighHighStylesPacket::styles),
                    ThighHighStylesPacket::new
                )
            );
        }

        @Override
        public Runnable handle(ThighHighStylesPacket message) {
            return () -> EstrogenItems.THIGH_HIGHS.get().loadStyles(message.styles());
        }
    }
}
