package dev.mayaqq.estrogen.networking.messages.s2c;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.EstrogenClientEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("ClassEscapesDefinedScope")
public record AdvancementUnlockClientPacket(ResourceLocation advancement) implements Packet<AdvancementUnlockClientPacket> {
    public static Handler HANDLER = new Handler();
    public static final ResourceLocation ID = Estrogen.id("advancement_unlock_client");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<AdvancementUnlockClientPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<AdvancementUnlockClientPacket> {
        @Override
        public void encode(AdvancementUnlockClientPacket message, FriendlyByteBuf buffer) {
            buffer.writeResourceLocation(message.advancement);
        }

        @Override
        public AdvancementUnlockClientPacket decode(FriendlyByteBuf buffer) {
            return new AdvancementUnlockClientPacket(buffer.readResourceLocation());
        }

        @Override
        public PacketContext handle(AdvancementUnlockClientPacket message) {
            return (player, level) -> {
                EstrogenClientEvents.onAdvancementUnlockClient(message.advancement);
            };
        }
    }
}
