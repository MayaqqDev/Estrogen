package dev.mayaqq.estrogen.utils;

import com.simibubi.create.foundation.utility.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum EstrogenColors {
    MOLTEN_SLIME(144, 238, 144),
    TESTOSTERONE_MIXTURE(232, 212, 171),
    FILTRATED_HORSE_URINE(225, 225, 20),
    HORSE_URINE(140, 139, 5),
    MOLTEN_AMETHYST(174, 122, 253),
    
    ESTROGEN_PATCHES_BAR(0, 179, 255);

    private static final Color[] DASH_OVERLAY = new Color[] {
            new Color(77, 128, 204).setImmutable(),
            new Color(253, 126, 247).darker().setImmutable()
    };

    public static Color getDashColor(int level, boolean particle) {
        if(level < 1) level = 1;
        if(level > DASH_OVERLAY.length) level = DASH_OVERLAY.length;
        return particle ? DASH_OVERLAY[level - 1].brighter() : DASH_OVERLAY[level - 1];
    }

    private final Color color;
    public final int value;

    EstrogenColors(int r, int g, int b) {
        this.color = new Color(r, g, b).setImmutable();
        this.value = color.getRGB();
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }


    public static record Meow(String name, int arf, int cat) {}

    public static Map<String, Integer> imperative(List<Meow> in) {
        Map<String, Integer> map = new HashMap<>();

        for(Meow meow : in) {
            map.put(meow.name, meow.arf);
        }

        return map;
    }

    public static Map<String, Integer> declarative(List<Meow> in) {
        return in.stream().collect(Collectors.toMap(meow -> meow.name, meow -> meow.arf));
    }

}
