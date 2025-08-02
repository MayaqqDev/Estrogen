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
        val THIGHS: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation("trinkets", "legs/thighs"))
        val CURIOS_THIGHS: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation("curios", "thighs"))
        val MUSIC_DISCS: TagKey<Item> = TagKey.create(Registries.ITEM, mcId("music_discs"))
        val UWUFYING: TagKey<Item> = TagKey.create(Registries.ITEM, id("uwufying"))
        val LAVA_BUCKETS: TagKey<Item> = TagKey.create(Registries.ITEM, commonId("lava_buckets"))
        val COOKIES: TagKey<Item> = TagKey.create(Registries.ITEM, commonId("cookies"))
        val CHEST_FEATURE_DISABLED: TagKey<Item> = TagKey.create(Registries.ITEM, id("chest_feature_disabled"))
        val LEATHER_ITEMS: TagKey<Item> = TagKey.create(Registries.ITEM, commonId("leather_items"))
        val LIGHT_EMITTERS: TagKey<Item> = TagKey.create(Registries.ITEM, commonId("light_emitters"))
        val MALUM_GROSS_FOODS: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation("malum", "gross_foods"))
        val CHEST_ARMOR_IGNORE: TagKey<Item> = TagKey.create(Registries.ITEM, id("chest_armor_ignore"))
        val NON_RECOLORABLE: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation("moonlight", "non_recolorable"))
        val MAGNET: TagKey<Item> = TagKey.create(Registries.ITEM, ResourceLocation("create_new_age", "magnet"))
        val UPRIGHT_ON_BELT = TagKey.create(Registries.ITEM, ResourceLocation("create", "upright_on_belt"))
    }

    object Blocks {
        val PICKAXE_MINABLE: TagKey<Block> = TagKey.create(Registries.BLOCK, mcId("mineable/pickaxe"))
        val MAGNET_12: TagKey<Block> = TagKey.create(Registries.BLOCK, ResourceLocation("create_new_age", "magnets/force_12"))
        val NON_RECOLORABLE: TagKey<Block> = TagKey.create(Registries.BLOCK, ResourceLocation("moonlight", "non_recolorable"))
    }

    object Fluids {
        val WATER: TagKey<Fluid> = TagKey.create(Registries.FLUID, mcId("water"))
        val LAVA: TagKey<Fluid> = TagKey.create(Registries.FLUID, mcId("lava"))
        val URINE: TagKey<Fluid> = TagKey.create(Registries.FLUID, id("urine"))
        val PROCESSING_LAVA: TagKey<Fluid> = TagKey.create(Registries.FLUID, ResourceLocation("create", "fan_processing_catalysts/blasting"))
        val SPONGE_IGNORING: TagKey<Fluid> = TagKey.create(Registries.FLUID, id("sponge_ignoring"))
    }

    object Entities {
        val URINE_GIVING: TagKey<EntityType<*>> = TagKey.create(Registries.ENTITY_TYPE, id("urine_giving"))
    }

    object Biomes {
        val SPAWNS_MOTH: TagKey<Biome> = TagKey.create(Registries.BIOME, id("spawns_moth"))
    }
}