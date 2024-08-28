package dev.mayaqq.estrogen.client.cosmetics;

import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Cosmetics {
    private static final Set<UUID> DISABLED = new HashSet<>();

    public static void init() {
        CosmeticsApi.init();
    }

    public static Cosmetic getCosmetic(UUID player) {
        if (DISABLED.contains(player)) return null;
        return CosmeticsApi.getCosmetic(player);
    }

    public static void setCosmeticShown(UUID player, boolean shown) {
        if (shown) DISABLED.remove(player);
        else DISABLED.add(player);
    }
}
