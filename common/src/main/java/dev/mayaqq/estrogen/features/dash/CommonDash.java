package dev.mayaqq.estrogen.features.dash;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommonDash {

    private static final long DASH_DURATION = 1000;

    private static final ConcurrentHashMap<UUID, Long> players = new ConcurrentHashMap<>();

    public static void setDashing(UUID player) {
        players.put(player, System.currentTimeMillis());
    }

    public static void removeDashing(UUID player) {
        players.remove(player);
    }

    public static boolean isPlayerDashing(UUID player) {
        return players.getOrDefault(player, 0L) + DASH_DURATION > System.currentTimeMillis();
    }
}
