package dev.mayaqq.estrogen.utils;

import net.minecraft.world.World;

import java.time.Duration;
import java.time.Instant;

public class Time {
    /**
     * Store the world time as a double precision floating point number.
     *
     * @param world The world to get the time of.
     * @return Current time. Number of ticks.
     */
    public static double currentTime(World world) {
        long ticks = world.getTime();
        if (ticks >= 0 && ticks <= Math.pow(2, 53)) {
            return (double) ticks;
        } else {
            throw new ArithmeticException("overflow");
        }
    }
}