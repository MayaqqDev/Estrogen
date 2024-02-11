package dev.mayaqq.estrogen.registry.common;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class EstrogenTags {
    public static class Items {
        public static final TagKey<Item> THIGHS = TagKey.create(Registry.ITEM.key(), new ResourceLocation("trinkets", "legs/thighs"));
        public static final TagKey<Item> CURIOS_THIGHS = TagKey.create(Registry.ITEM.key(), new ResourceLocation("curios", "thighs"));
        public static final TagKey<Item> MUSIC_DISCS = TagKey.create(Registry.ITEM.key(), mcId("music_discs"));
        public static final TagKey<Item> UWUFYING = TagKey.create(Registry.ITEM.key(), new ResourceLocation("estrogen", "uwufying"));
        public static final TagKey<Item> COPPER_PLATES = TagKey.create(Registry.ITEM.key(), new ResourceLocation("estrogen", "copper_plates"));
    }

    public static class Blocks {
        public static final TagKey<Block> PICKAXE_MINABLE = TagKey.create(Registry.BLOCK.key(), new ResourceLocation("minecraft", "mineable/pickaxe"));
    }

    public static class Fluids {
        public static final TagKey<Fluid> WATER = TagKey.create(Registry.FLUID.key(), mcId("water"));
        public static final TagKey<Fluid> LAVA = TagKey.create(Registry.FLUID.key(), mcId("lava"));
    }

    private static ResourceLocation mcId(String path) {
        return new ResourceLocation("minecraft", path);
    }
}