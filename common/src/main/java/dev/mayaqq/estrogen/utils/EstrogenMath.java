package dev.mayaqq.estrogen.utils;

import net.minecraft.util.Mth;

public class EstrogenMath {
    public static float boobEquation(float level) {
        return level * level * 0.3F;
    }

    public static float boobFunc(float level) {
        return 1.26F - Mth.invSqrt((level + 0.6F)*0.95F);
    }
}
