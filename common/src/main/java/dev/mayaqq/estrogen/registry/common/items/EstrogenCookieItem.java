package dev.mayaqq.estrogen.registry.common.items;

import dev.mayaqq.estrogen.registry.common.EstrogenSounds;
import net.minecraft.world.item.RecordItem;

public class EstrogenCookieItem extends RecordItem {
    public EstrogenCookieItem(Properties properties) {
        // Redstone Signal Strength, Sound, Properties, Duration
        super(3, EstrogenSounds.G03C.get(), properties);
    }
}
