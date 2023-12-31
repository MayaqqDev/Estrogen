package dev.mayaqq.estrogen.utils;

import net.minecraft.world.level.Level;

public class Time {
    /**
     * Store the world time as a double precision floating point number.
     *
     * @param world The world to get the time of.
     * @return Current time. Number of ticks.
     */
    public static double currentTime(Level world) {
        long ticks = world.getGameTime();
        if (ticks >= 0 && ticks <= Math.pow(2, 53)) {
            return (double) ticks;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}