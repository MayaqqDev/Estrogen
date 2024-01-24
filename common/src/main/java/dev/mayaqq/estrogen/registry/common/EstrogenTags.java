package dev.mayaqq.estrogen.registry.common;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EstrogenTags {
    public static final TagKey<Item> UWUFYING = TagKey.create(Registry.ITEM.key(), new ResourceLocation("estrogen", "uwufying"));
    public static final TagKey<Item> COPPER_PLATES = TagKey.create(Registry.ITEM.key(), new ResourceLocation("estrogen", "copper_plates"));

}
