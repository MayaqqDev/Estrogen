package dev.mayaqq.estrogen.networking;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectRemovalS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;

import static dev.mayaqq.estrogen.networking.EstrogenS2C.REMOVE_STATUS_EFFECT_PACKET_ID;
import static dev.mayaqq.estrogen.networking.EstrogenS2C.STATUS_EFFECT_PACKET_ID;

public class EstrogenStatusEffectSender {
    public static void sendPlayerStatusEffect(ServerPlayerEntity player, StatusEffect effect, ServerPlayerEntity... targetPlayers) {
        StatusEffectInstance effectInstance = player.getStatusEffect(effect);
        if (effectInstance != null) {
            Packet<ClientPlayPacketListener> packet = new EntityStatusEffectUpdateS2CPacket(player.getId(), effectInstance);
            PacketByteBuf buf = PacketByteBufs.create();
            packet.write(buf);
            for(ServerPlayerEntity targetPlayer: targetPlayers) {
                ServerPlayNetworking.send(targetPlayer, STATUS_EFFECT_PACKET_ID, buf);
            }
        }
    }

    public static void sendRemovePlayerStatusEffect(ServerPlayerEntity player, StatusEffect effect, ServerPlayerEntity... targetPlayers) {
        Packet<ClientPlayPacketListener> packet = new EntityStatusEffectRemovalS2CPacket(player.getId(), effect);
        PacketByteBuf buf = PacketByteBufs.create();
        packet.write(buf);
        for(ServerPlayerEntity targetPlayer: targetPlayers) {
            ServerPlayNetworking.send(targetPlayer, REMOVE_STATUS_EFFECT_PACKET_ID, buf);
        }
    }
}
