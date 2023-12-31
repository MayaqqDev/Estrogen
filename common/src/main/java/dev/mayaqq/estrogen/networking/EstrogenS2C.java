package dev.mayaqq.estrogen.networking;

import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceLocation;

public class EstrogenS2C {
    public static final ResourceLocation STATUS_EFFECT_PACKET_ID = Estrogen.id("status_effect");
    public static final ResourceLocation REMOVE_STATUS_EFFECT_PACKET_ID = Estrogen.id("remove_status_effect");

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(STATUS_EFFECT_PACKET_ID, (client, handler, buf, responseSender) -> {
            FriendlyByteBuf buffer = PacketByteBufs.copy(buf);
            client.execute(() -> {
                ClientboundUpdateMobEffectPacket packet = new ClientboundUpdateMobEffectPacket(buffer);
                client.getConnection().handleUpdateMobEffect(packet);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(REMOVE_STATUS_EFFECT_PACKET_ID, (client, handler, buf, responseSender) -> {
            FriendlyByteBuf buffer = PacketByteBufs.copy(buf);
            client.execute(() -> {
                ClientboundUpdateMobEffectPacket packet = new ClientboundUpdateMobEffectPacket(buffer);
                client.getConnection().handleUpdateMobEffect(packet);
            });
        });
    }
}
