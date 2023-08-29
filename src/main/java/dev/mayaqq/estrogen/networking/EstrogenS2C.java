package dev.mayaqq.estrogen.networking;

import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;
import net.minecraft.util.Identifier;

public class EstrogenS2C {
    public static final Identifier STATUS_EFFECT_PACKET_ID = Estrogen.id("status_effect");
    public static final Identifier REMOVE_STATUS_EFFECT_PACKET_ID = Estrogen.id("remove_status_effect");

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(STATUS_EFFECT_PACKET_ID, (client, handler, buf, responseSender) -> {
            PacketByteBuf buffer = PacketByteBufs.copy(buf);
            client.execute(() -> {
                EntityStatusEffectS2CPacket packet = new EntityStatusEffectS2CPacket(buffer);
                client.getNetworkHandler().onEntityStatusEffect(packet);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(REMOVE_STATUS_EFFECT_PACKET_ID, (client, handler, buf, responseSender) -> {
            PacketByteBuf buffer = PacketByteBufs.copy(buf);
            client.execute(() -> {
                RemoveEntityStatusEffectS2CPacket packet = new RemoveEntityStatusEffectS2CPacket(buffer);
                client.getNetworkHandler().onRemoveEntityStatusEffect(packet);
            });
        });
    }
}
