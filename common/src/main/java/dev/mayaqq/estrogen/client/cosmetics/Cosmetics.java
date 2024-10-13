package dev.mayaqq.estrogen.client.cosmetics;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Cosmetics {
    private static final Set<UUID> DISABLED = new HashSet<>();

    public static final Logger LOGGER = LoggerFactory.getLogger(Estrogen.MOD_ID + "/cosmetics");

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
