package dev.mayaqq.estrogen.utils;

import java.awt.*;

public enum EstrogenColors {
    MOLTEN_SLIME(144, 238, 144),
    TESTOSTERONE_MIXTURE(232, 212, 171),
    FILTRATED_HORSE_URINE(225, 225, 20),
    HORSE_URINE(140, 139, 5),
    MOLTEN_AMETHYST(174, 122, 253),
    
    ESTROGEN_PATCHES_BAR(0, 179, 255);

    private final Color color;
    public final int value;

    EstrogenColors(int r, int g, int b) {
        this.color = new Color(r, g, b);
        this.value = color.getRGB();
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }
}
