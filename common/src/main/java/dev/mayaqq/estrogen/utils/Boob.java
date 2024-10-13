package dev.mayaqq.estrogen.utils;

import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class Boob {
    /**
     * Calculate boob size for rendering based on the time the effect was applied.
     *
     * @param startTime   The time when the effect was applied. World time in ticks.
     * @param currentTime Current world time in ticks.
     * @param initialSize Size between 0 and 1. Added to the calculated size.
     * @param tickDelta   Time elapsed since the last tick.
     * @return Boob size. Floating point value between 0 and 1.
     */
    public static float boobSize(double startTime, double currentTime, float initialSize, float tickDelta) {
        return Mth.clamp(((float) (currentTime - startTime + tickDelta) * 50 / 20000) + initialSize, 0.0F, 1.0F);
    }

    public static boolean shouldShow(Player player) {
        return player.getAttributeValue(EstrogenAttributes.SHOW_BOOBS.get()) > 0.0;
    }
}
