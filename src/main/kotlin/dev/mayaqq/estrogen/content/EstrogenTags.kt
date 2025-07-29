package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.id
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid

object EstrogenTags {
    private fun mcId(path: String): ResourceLocation {
        return ResourceLocation("minecraft", path)
    }

    private fun commonId(path: String): ResourceLocation {
        return ResourceLocation("c", path)
    }

    object Items {
        val THIGHS: TagKey<Item> =
            TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation("trinkets", "legs/thighs"))
        val CURIOS_THIGHS: TagKey<Item> =
            TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation("curios", "thighs"))
        val MUSIC_DISCS: TagKey<Item> = TagKey.create(BuiltInRegistries.ITEM.key(), mcId("music_discs"))
        val UWUFYING: TagKey<Item> = TagKey.create(BuiltInRegistries.ITEM.key(), id("uwufying"))
        val LAVA_BUCKETS: TagKey<Item> = TagKey.create(BuiltInRegistries.ITEM.key(), commonId("lava_buckets"))
        val COOKIES: TagKey<Item> = TagKey.create(BuiltInRegistries.ITEM.key(), commonId("cookies"))
        val CHEST_FEATURE_DISABLED: TagKey<Item> =
            TagKey.create(BuiltInRegistries.ITEM.key(), id("chest_feature_disabled"))
        val LEATHER_ITEMS: TagKey<Item> = TagKey.create(BuiltInRegistries.ITEM.key(), commonId("leather_items"))
        val LIGHT_EMITTERS: TagKey<Item> = TagKey.create(BuiltInRegistries.ITEM.key(), commonId("light_emitters"))
        val MALUM_GROSS_FOODS: TagKey<Item> =
            TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation("malum", "gross_foods"))
        val CHEST_ARMOR_IGNORE: TagKey<Item> =
            TagKey.create(BuiltInRegistries.ITEM.key(), id("chest_armor_ignore"))
        val NON_RECOLORABLE: TagKey<Item> =
            TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation("moonlight", "non_recolorable"))
        val MAGNET: TagKey<Item> =
            TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation("create_new_age", "magnet"))
        val UPRIGHT_ON_BELT = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation("create", "upright_on_belt"))
    }

    object Blocks {
        val PICKAXE_MINABLE: TagKey<Block> = TagKey.create(BuiltInRegistries.BLOCK.key(), mcId("mineable/pickaxe"))
        val MAGNET_12: TagKey<Block> =
            TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation("create_new_age", "magnets/force_12"))
        val NON_RECOLORABLE: TagKey<Block> =
            TagKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation("moonlight", "non_recolorable"))
    }

    object Fluids {
        val WATER: TagKey<Fluid> = TagKey.create(BuiltInRegistries.FLUID.key(), mcId("water"))
        val LAVA: TagKey<Fluid> = TagKey.create(BuiltInRegistries.FLUID.key(), mcId("lava"))
        val URINE: TagKey<Fluid> = TagKey.create(BuiltInRegistries.FLUID.key(), id("urine"))
        val PROCESSING_LAVA: TagKey<Fluid> = TagKey.create(BuiltInRegistries.FLUID.key(), ResourceLocation("create", "fan_processing_catalysts/blasting"))
    }

    object Entities {
        val URINE_GIVING: TagKey<EntityType<*>> =
            TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), id("urine_giving"))
    }

    object Biomes {
        val SPAWNS_MOTH: TagKey<Biome> = TagKey.create(Registries.BIOME, id("spawns_moth"))
    }
}