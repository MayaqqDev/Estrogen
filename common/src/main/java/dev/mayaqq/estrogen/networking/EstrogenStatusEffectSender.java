package dev.mayaqq.estrogen.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.lang.reflect.UndeclaredThrowableException;

public class EstrogenStatusEffectSender { //idk if this'll work tbh im an idiot ngl -bagel

    public void sendPlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        MobEffectInstance effectInstance = player.getEffect(effect);
        if (effectInstance != null) {
            Packet<ClientGamePacketListener> packet = new ClientboundUpdateMobEffectPacket(player.getId(), effectInstance);
            FriendlyByteBuf buf = PacketByteBufs.create();
            packet.write(buf);
            for(ServerPlayer targetPlayer: targetPlayers) {
                ServerPlayNetworking.send(targetPlayer, EstrogenS2C.STATUS_EFFECT_PACKET_ID, buf);
            }
        }
    }

    public void sendRemovePlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        MobEffectInstance effectInstance = player.getEffect(effect);
        if (effectInstance != null) {
            Packet<ClientGamePacketListener> packet = new ClientboundUpdateMobEffectPacket(player.getId(), effectInstance);
            FriendlyByteBuf buf = PacketByteBufs.create();
            packet.write(buf);
            for (ServerPlayer targetPlayer : targetPlayers) {
                ServerPlayNetworking.send(targetPlayer, EstrogenS2C.REMOVE_STATUS_EFFECT_PACKET_ID, buf);
            }
        }
    }
}
