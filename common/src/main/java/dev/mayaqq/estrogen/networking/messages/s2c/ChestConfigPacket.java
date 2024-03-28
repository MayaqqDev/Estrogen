package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

@SuppressWarnings("ClassEscapesDefinedScope")
public record ChestConfigPacket(UUID uuid, boolean enabled, boolean armorEnabled, boolean physicsEnabled, float bounciness, float damping) implements Packet<ChestConfigPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("player_chest_config");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ChestConfigPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ChestConfigPacket> {

        @Override
        public void encode(ChestConfigPacket message, FriendlyByteBuf buffer) {
            buffer.writeUUID(message.uuid);
            buffer.writeBoolean(message.enabled);
            buffer.writeBoolean(message.armorEnabled);
            buffer.writeBoolean(message.physicsEnabled);
            buffer.writeFloat(message.bounciness);
            buffer.writeFloat(message.damping);
        }

        @Override
        public ChestConfigPacket decode(FriendlyByteBuf buffer) {
            return new ChestConfigPacket(buffer.readUUID(), buffer.readBoolean(), buffer.readBoolean(), buffer.readBoolean(), buffer.readFloat(), buffer.readFloat());
        }

        @Override
        public PacketContext handle(ChestConfigPacket message) {
            return (player, level) -> {
                ((PlayerEntityExtension) level.getPlayerByUUID(message.uuid)).estrogen$setChestConfig(new ChestConfig(message.enabled, message.armorEnabled, message.physicsEnabled, message.bounciness, message.damping));
            };
        }
    }
}
