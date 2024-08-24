package dev.mayaqq.estrogen.client.cosmetics;

import com.teamresourceful.resourcefulcosmetics.ResourcefulCosmetics;
import dev.mayaqq.estrogen.Estrogen;

public class CosmeticsApi {
    private static final ResourcefulCosmetics<Cosmetic> API = ResourcefulCosmetics.create(
            "https://estrogen-cosmetics.teamresourceful.com",
            Cosmetic::fromJson,
            error -> Estrogen.LOGGER.error("Failed to load cosmetics", error)
    )
}
