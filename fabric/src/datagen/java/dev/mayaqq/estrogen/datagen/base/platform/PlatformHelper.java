package dev.mayaqq.estrogen.datagen.base.platform;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public interface PlatformHelper {

    ModPlatform getPlatform();

    String getName(String name);

    TagKey<Item> getCommonTag(String name);
}
