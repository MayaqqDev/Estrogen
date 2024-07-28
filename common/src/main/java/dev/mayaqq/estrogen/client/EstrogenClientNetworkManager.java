package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.client.registry.EstrogenClientEvents;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.networking.messages.s2c.AdvancementUnlockClientPacket;
import dev.mayaqq.estrogen.networking.messages.s2c.ChestConfigPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

/**
 * This class handles the client network packets.
 * All s2c packets should be handled here as things like Minecraft.getInstance() are only available on the client side.
 */
public class EstrogenClientNetworkManager {

    public static void handleChestConfig(ChestConfigPacket packet) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        PlayerEntityExtension player = (PlayerEntityExtension) level.getPlayerByUUID(packet.uuid());
        if (player == null) return;
        player.estrogen$setChestConfig(packet.config());
    }

    public static void handleAdvancementUnlockClient(AdvancementUnlockClientPacket packet) {
        EstrogenClientEvents.onAdvancementUnlockClient(packet.advancement());
    }
}
