package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class EstrogenTags {
    public static class Items {
        public static final TagKey<Item> THIGHS = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("trinkets", "legs/thighs"));
        public static final TagKey<Item> CURIOS_THIGHS = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("curios", "thighs"));
        public static final TagKey<Item> MUSIC_DISCS = TagKey.create(BuiltInRegistries.ITEM.key(), mcId("music_discs"));
        public static final TagKey<Item> UWUFYING = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("estrogen", "uwufying"));
        public static final TagKey<Item> LAVA_BUCKETS = TagKey.create(BuiltInRegistries.ITEM.key(), commonId("lava_buckets"));
        public static final TagKey<Item> COOKIES = TagKey.create(BuiltInRegistries.ITEM.key(), commonId("cookies"));
        public static final TagKey<Item> CHEST_FEATURE_DISABLED = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("estrogen", "chest_feature_disabled"));
        public static final TagKey<Item> LEATHER_ITEMS = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("c", "leather_items"));
        public static final TagKey<Item> LIGHT_EMITTERS = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("c", "light_emitters"));
    }

    public static class Blocks {
        public static final TagKey<Block> PICKAXE_MINABLE = TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("minecraft", "mineable/pickaxe"));
        public static final TagKey<Block> MAGNET_OLD = TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("create_new_age", "custom_magnet"));
        public static final TagKey<Block> MAGNET = TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("create_new_age", "magnet"));
        public static final TagKey<Block> MAGNET_12_OLD = TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("create_new_age", "magnets/strength_12"));
        public static final TagKey<Block> MAGNET_12 = TagKey.create(BuiltInRegistries.BLOCK.key(), new ResourceLocation("create_new_age", "magnets/force_12"));
    }

    public static class Fluids {
        public static final TagKey<Fluid> WATER = TagKey.create(BuiltInRegistries.FLUID.key(), mcId("water"));
        public static final TagKey<Fluid> LAVA = TagKey.create(BuiltInRegistries.FLUID.key(), mcId("lava"));
        public static final TagKey<Fluid> URINE = TagKey.create(BuiltInRegistries.FLUID.key(), Estrogen.id("urine"));
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> URINE_GIVING = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), Estrogen.id("urine_giving"));
    }

    public static class Biomes {
        public static final TagKey<Biome> SPAWNS_MOTH = TagKey.create(Registries.BIOME, Estrogen.id("spawns_moth"));
    }

    private static ResourceLocation mcId(String path) {
        return new ResourceLocation("minecraft", path);
    }

    private static ResourceLocation commonId(String path) {
        return new ResourceLocation("c", path);
    }
}