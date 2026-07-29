package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.modId
import dev.mayaqq.cynosure.utils.*
import dev.mayaqq.estrogen.id
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
        return identifier("minecraft", path)
    }

    private fun commonId(path: String): ResourceLocation {
        return identifier("c", path)
    }

    object Items {
        val THIGHS: TagKey<Item> = itemTag(identifier("trinkets", "legs/thighs"))
        val CURIOS_THIGHS: TagKey<Item> = itemTag(identifier("curios", "thighs"))
        val UWUFYING: TagKey<Item> = itemTag(id("uwufying"))
        val LAVA_BUCKETS: TagKey<Item> = itemTag(commonId("lava_buckets"))
        val COOKIES: TagKey<Item> = itemTag(commonId("foods/cookie"))
        val CHEST_FEATURE_DISABLED: TagKey<Item> = itemTag(id("chest_feature_disabled"))
        val LEATHER_ITEMS: TagKey<Item> = itemTag(commonId("leather_items"))
        val LIGHT_EMITTERS: TagKey<Item> = itemTag(commonId("light_emitters"))
        val MALUM_GROSS_FOODS: TagKey<Item> = itemTag(identifier("malum", "gross_foods"))
        val CHEST_ARMOR_IGNORE: TagKey<Item> = itemTag(id("chest_armor_ignore"))
        val NON_RECOLORABLE: TagKey<Item> = itemTag(identifier("moonlight", "non_recolorable"))
        val MAGNET: TagKey<Item> = itemTag(identifier("create_new_age", "magnet"))
        val UPRIGHT_ON_BELT = itemTag(identifier("create", "upright_on_belt"))
        val DISABLES_CAPE = itemTag(modId("disables_cape"))
        val TWILIGHT_UNCRAFTING_BLACKLIST = itemTag(identifier("twilightforest", "banned_uncrafting_ingredients"))
        val TWILIGHT_UNCRAFTING_BLACKLIST_2_WHAT = itemTag(identifier("twilightforest", "banned_uncraftables"))
        val HEAD_ENCHANTABLE = itemTag(id("enchantable/head"))
        val HIDDEN_FROM_RECIPE_VIEWERS = itemTag(commonId("hidden_from_recipe_viewers"))
    }

    object Blocks {
        val PICKAXE_MINABLE: TagKey<Block> = blockTag(mcId("mineable/pickaxe"))
        val MAGNET_12: TagKey<Block> = blockTag(identifier("create_new_age", "magnets/force_12"))
        val NON_RECOLORABLE: TagKey<Block> = blockTag(identifier("moonlight", "non_recolorable"))
        val HIDDEN_FROM_RECIPE_VIEWERS = blockTag(commonId("hidden_from_recipe_viewers"))
    }

    object Fluids {
        val WATER: TagKey<Fluid> = fluidTag(mcId("water"))
        val LAVA: TagKey<Fluid> = fluidTag(mcId("lava"))
        val URINE: TagKey<Fluid> = fluidTag(id("urine"))
        val PROCESSING_LAVA: TagKey<Fluid> = fluidTag(identifier("create", "fan_processing_catalysts/blasting"))
        val SPONGE_IGNORING: TagKey<Fluid> = fluidTag(id("sponge_ignoring"))
    }

    object Entities {
        val URINE_GIVING: TagKey<EntityType<*>> = entityTypeTag(id("urine_giving"))
    }

    object Biomes {
        val SPAWNS_MOTH: TagKey<Biome> = Registries.BIOME.tag(id("spawns_moth"))
    }
}