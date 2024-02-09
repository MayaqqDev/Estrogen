package dev.mayaqq.estrogen.networking;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class EstrogenStatusEffectSender {

    public void sendPlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        MobEffectInstance effectInstance = player.getEffect(effect);
        if (effectInstance != null) {
            Packet<ClientGamePacketListener> packet = new ClientboundUpdateMobEffectPacket(player.getId(), effectInstance);
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            packet.write(buf);
            for (ServerPlayer targetPlayer : targetPlayers) {
                NetworkManager.sendToPlayer(targetPlayer, EstrogenS2C.STATUS_EFFECT_PACKET_ID, buf);
            }
        }
    }

    public void sendRemovePlayerStatusEffect(ServerPlayer player, MobEffect effect, ServerPlayer... targetPlayers) {
        MobEffectInstance effectInstance = player.getEffect(effect);
        if (effectInstance != null) {
            Packet<ClientGamePacketListener> packet = new ClientboundUpdateMobEffectPacket(player.getId(), effectInstance);
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            packet.write(buf);
            for (ServerPlayer targetPlayer : targetPlayers) {
                NetworkManager.sendToPlayer(targetPlayer, EstrogenS2C.REMOVE_STATUS_EFFECT_PACKET_ID, buf);
            }
        }
    }
}
