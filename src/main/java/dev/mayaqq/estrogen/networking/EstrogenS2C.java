package dev.mayaqq.estrogen.networking;

import dev.mayaqq.estrogen.Estrogen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class EstrogenS2C {
    public static final Identifier STATUS_EFFECT_PACKET_ID = Estrogen.id("status_effect");
    public static final Identifier REMOVE_STATUS_EFFECT_PACKET_ID = Estrogen.id("remove_status_effect");

    public static void sendPlayerStatusEffect(ServerPlayerEntity player, StatusEffect effect, ServerPlayerEntity... targetPlayers) {
        StatusEffectInstance effectInstance = player.getStatusEffect(effect);
        if (effectInstance != null) {
            Packet packet = new EntityStatusEffectS2CPacket(player.getId(), effectInstance);
            PacketByteBuf buf = PacketByteBufs.create();
            packet.write(buf);
            for(ServerPlayerEntity targetPlayer: targetPlayers) {
                ServerPlayNetworking.send(targetPlayer, STATUS_EFFECT_PACKET_ID, buf);
            }
        }
    }

    public static void sendRemovePlayerStatusEffect(ServerPlayerEntity player, StatusEffect effect, ServerPlayerEntity... targetPlayers) {
        Packet packet = new RemoveEntityStatusEffectS2CPacket(player.getId(), effect);
        PacketByteBuf buf = PacketByteBufs.create();
        packet.write(buf);
        for(ServerPlayerEntity targetPlayer: targetPlayers) {
            ServerPlayNetworking.send(targetPlayer, REMOVE_STATUS_EFFECT_PACKET_ID, buf);
        }
    }


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
