package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("ClassEscapesDefinedScope")
public record StatusEffectPacket(FriendlyByteBuf buf) implements Packet<StatusEffectPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("status_effect");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<StatusEffectPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<StatusEffectPacket> {

        @Override
        public void encode(StatusEffectPacket message, FriendlyByteBuf buffer) {}

        @Override
        public StatusEffectPacket decode(FriendlyByteBuf buffer) {
            return new StatusEffectPacket(buffer);
        }

        @Override
        public PacketContext handle(StatusEffectPacket message) {
            return (player, level) -> {
                Minecraft client = Minecraft.getInstance();
                ClientboundUpdateMobEffectPacket packet = new ClientboundUpdateMobEffectPacket(message.buf);
                if (client.getConnection() != null) client.getConnection().handleUpdateMobEffect(packet);
            };
        }
    }
}
