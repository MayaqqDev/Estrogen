package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("ClassEscapesDefinedScope")
public class RemoveStatusEffectPacket implements Packet<RemoveStatusEffectPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("remove_status_effect");
    public FriendlyByteBuf buffer;

    public RemoveStatusEffectPacket(FriendlyByteBuf buf) {
        this.buffer = new FriendlyByteBuf(buf.copy());
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<RemoveStatusEffectPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<RemoveStatusEffectPacket> {

        @Override
        public void encode(RemoveStatusEffectPacket message, FriendlyByteBuf buffer) {
            buffer.writeBytes(message.buffer);
        }

        @Override
        public RemoveStatusEffectPacket decode(FriendlyByteBuf buffer) {
            return new RemoveStatusEffectPacket(buffer);
        }

        @Override
        public PacketContext handle(RemoveStatusEffectPacket message) {
            return (player, level) -> {
                Minecraft client = Minecraft.getInstance();
                ClientboundRemoveMobEffectPacket packet = new ClientboundRemoveMobEffectPacket(message.buffer);
                if (client.getConnection() != null) client.getConnection().handleRemoveMobEffect(packet);
            };
        }
    }
}
