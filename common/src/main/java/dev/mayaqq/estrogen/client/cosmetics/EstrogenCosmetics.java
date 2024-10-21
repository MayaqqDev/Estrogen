package dev.mayaqq.estrogen.client.cosmetics;

import dev.mayaqq.estrogen.client.cosmetics.model.animation.registry.Interpolations;
import dev.mayaqq.estrogen.client.cosmetics.model.animation.registry.VectorTypes;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EstrogenCosmetics {
    private static final Set<UUID> DISABLED = new HashSet<>();

    public static final Logger LOGGER = LoggerFactory.getLogger(EstrogenCosmetics.class);

    private static int animationTicks = 0;

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

    public static void tick() {
        if(animationTicks == 30000000) animationTicks = 0;
        animationTicks++;
    }

    public static long getAnimationTicks() {
        return (long) ((double) (Mth.lerp(Minecraft.getInstance().getFrameTime(), animationTicks, animationTicks + 1)) * 50L);
    }
}
