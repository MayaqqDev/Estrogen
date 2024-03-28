package dev.mayaqq.estrogen.networking.messages.c2s;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("ClassEscapesDefinedScope")
public record SetChestConfigPacket(Boolean enabled, boolean armorEnabled, boolean physicsEnabled, float bounciness, float damping) implements Packet<SetChestConfigPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("chest_feature_config");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<SetChestConfigPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<SetChestConfigPacket> {

        @Override
        public void encode(SetChestConfigPacket message, FriendlyByteBuf buffer) {
            buffer.writeBoolean(message.enabled);
            buffer.writeBoolean(message.armorEnabled);
            buffer.writeBoolean(message.physicsEnabled);
            buffer.writeFloat(message.bounciness);
            buffer.writeFloat(message.damping);
        }

        @Override
        public SetChestConfigPacket decode(FriendlyByteBuf buffer) {
            return new SetChestConfigPacket(buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readFloat(), buffer.readFloat());
        }

        @Override
        public PacketContext handle(SetChestConfigPacket message) {
            return (player, level) -> {
                ((PlayerEntityExtension) player).estrogen$setChestConfig(new ChestConfig(message.enabled, message.armorEnabled, message.physicsEnabled, message.bounciness, message.damping));
                EstrogenNetworkManager.CHANNEL.sendToPlayersInLevel(new ChestConfigPacket(player.getUUID(), message.enabled, message.armorEnabled, message.physicsEnabled, message.bounciness, message.damping), level);
            };
        }
    }
}
