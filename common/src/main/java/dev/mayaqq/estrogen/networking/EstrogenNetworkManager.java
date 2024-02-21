package dev.mayaqq.estrogen.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.networking.messages.c2s.DashPacket;
import net.minecraft.resources.ResourceLocation;

public class EstrogenNetworkManager {
    public static final NetworkChannel CHANNEL = new NetworkChannel(Estrogen.MOD_ID, 0, "main");

    public static final ResourceLocation DASH_PARTICLES = Estrogen.id("dashparticles");
    public static final ResourceLocation SPAWN_HEARTS = Estrogen.id("spawnhearts");

    public static void reigster() {
        CHANNEL.registerPacket(NetworkDirection.CLIENT_TO_SERVER, DashPacket.ID, DashPacket.HANDLER, DashPacket.class);
    }
}
