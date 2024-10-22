package dev.mayaqq.estrogen.client.cosmetics;

import dev.mayaqq.estrogen.client.cosmetics.model.animation.registry.Interpolations;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.registry.VectorTypes;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EstrogenCosmetics {
    private static final Set<UUID> DISABLED = new HashSet<>();

    public static final Logger LOGGER = LoggerFactory.getLogger(EstrogenCosmetics.class);

    public static void init() {
        CosmeticsApi.init();
        VectorTypes.register();
        Interpolations.register();
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
