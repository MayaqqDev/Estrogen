package dev.mayaqq.estrogen.registry.items;

import dev.mayaqq.estrogen.registry.EstrogenSounds;
import net.minecraft.world.item.RecordItem;

public class EstrogenCookieItem extends RecordItem {
    public EstrogenCookieItem(Properties properties) {
        // Redstone Signal Strength, Sound, Properties, Duration
        super(3, EstrogenSounds.G03C.get(), properties, 303);
    }
}
