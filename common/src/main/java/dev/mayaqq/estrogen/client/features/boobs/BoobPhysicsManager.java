package dev.mayaqq.estrogen.client.features.boobs;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.utils.Boob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BoobPhysicsManager {

    private static final ConcurrentHashMap<UUID, Physics> players = new ConcurrentHashMap<>();

    public static boolean isEnabled() {
        return EstrogenConfig.client().chestPhysicsRendering.get();
    }

    public static void tick() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        if (!isEnabled()) return;
        for (ConcurrentHashMap.Entry<UUID, Physics> physics : players.entrySet()) {
            Player player = level.getPlayerByUUID(physics.getKey());
            if (player != null && Boob.shouldShow(player)) {
                physics.getValue().update(player);
                if (physics.getValue().expired) {
                    players.remove(physics.getKey());
                }
            } else {
                players.remove(physics.getKey());
            }
        }
    }

    public static Physics getPhysicsForPlayer(Player player) {
        return players.computeIfAbsent(player.getUUID(), uuid -> new Physics());
    }
}
