package dev.mayaqq.estrogen.networking;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import dev.architectury.networking.NetworkManager;
import dev.mayaqq.estrogen.Estrogen;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceLocation;

public class EstrogenS2C {
    public static final ResourceLocation STATUS_EFFECT_PACKET_ID = Estrogen.id("status_effect");
    public static final ResourceLocation REMOVE_STATUS_EFFECT_PACKET_ID = Estrogen.id("remove_status_effect");

    public static void register() {
        handle(STATUS_EFFECT_PACKET_ID);
        handle(REMOVE_STATUS_EFFECT_PACKET_ID);
    }

    private static void handle(ResourceLocation removeStatusEffectPacketId) {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, removeStatusEffectPacketId, (buf, context) -> {
            FriendlyByteBuf buffer = new FriendlyByteBuf(buf.copy());
            Minecraft client = Minecraft.getInstance();
            client.execute(() -> {
                ClientboundUpdateMobEffectPacket packet = new ClientboundUpdateMobEffectPacket(buffer);
                client.getConnection().handleUpdateMobEffect(packet);
            });
        });
    }
}
