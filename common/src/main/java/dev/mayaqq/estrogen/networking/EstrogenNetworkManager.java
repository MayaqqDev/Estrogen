package dev.mayaqq.estrogen.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.networking.messages.c2s.DashPacket;
import dev.mayaqq.estrogen.networking.messages.c2s.FinishLoadingPacket;
import dev.mayaqq.estrogen.networking.messages.c2s.SetChestConfigPacket;
import dev.mayaqq.estrogen.networking.messages.c2s.SpawnHeartsPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.RemoveStatusEffectPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.StatusEffectPacket;

public class EstrogenNetworkManager {
    public static final NetworkChannel CHANNEL = new NetworkChannel(Estrogen.MOD_ID, 0, "main");

    public static final EstrogenNetworkManager NETWORK_MANAGER = new EstrogenNetworkManager();

    public void init() {
        // C2S
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, DashPacket.ID, DashPacket.HANDLER, DashPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, SpawnHeartsPacket.ID, SpawnHeartsPacket.HANDLER, SpawnHeartsPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, FinishLoadingPacket.ID, FinishLoadingPacket.HANDLER, FinishLoadingPacket.class);
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, SetChestConfigPacket.ID, SetChestConfigPacket.HANDLER, SetChestConfigPacket.class);

        // S2C
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, StatusEffectPacket.ID, StatusEffectPacket.HANDLER, StatusEffectPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, RemoveStatusEffectPacket.ID, RemoveStatusEffectPacket.HANDLER, RemoveStatusEffectPacket.class);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ChestConfigPacket.ID, ChestConfigPacket.HANDLER, ChestConfigPacket.class);
    }
}
