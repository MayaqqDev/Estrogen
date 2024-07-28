package dev.mayaqq.estrogen.networking;

import com.teamresourceful.resourcefullib.common.network.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.networking.messages.c2s.DashPacket;
import dev.mayaqq.estrogen.networking.messages.c2s.FinishLoadingPacket;
import dev.mayaqq.estrogen.networking.messages.c2s.SetChestConfigPacket;
import dev.mayaqq.estrogen.networking.messages.c2s.SpawnHeartsPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.AdvancementUnlockClientPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;

public class EstrogenNetworkManager {
    public static final NetworkChannel CHANNEL = new NetworkChannel(Estrogen.MOD_ID, 0, "main");

    public static final EstrogenNetworkManager NETWORK_MANAGER = new EstrogenNetworkManager();

    public void init() {
        // C2S
        CHANNEL.register(DashPacket.TYPE);
        CHANNEL.register(SpawnHeartsPacket.TYPE);
        CHANNEL.register(FinishLoadingPacket.TYPE);
        CHANNEL.register(SetChestConfigPacket.TYPE);

        // S2C
        CHANNEL.register(ChestConfigPacket.TYPE);
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, AdvancementUnlockClientPacket.ID, AdvancementUnlockClientPacket.HANDLER, AdvancementUnlockClientPacket.class);
    }
}
