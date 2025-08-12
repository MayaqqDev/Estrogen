package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.modId
import dev.mayaqq.cynosure.utils.blockTag
import dev.mayaqq.cynosure.utils.entityTypeTag
import dev.mayaqq.cynosure.utils.fluidTag
import dev.mayaqq.cynosure.utils.itemTag
import dev.mayaqq.cynosure.utils.tag
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
        val THIGHS: TagKey<Item> = itemTag(ResourceLocation("trinkets", "legs/thighs"))
        val CURIOS_THIGHS: TagKey<Item> = itemTag(ResourceLocation("curios", "thighs"))
        val MUSIC_DISCS: TagKey<Item> = itemTag(mcId("music_discs"))
        val UWUFYING: TagKey<Item> = itemTag(id("uwufying"))
        val LAVA_BUCKETS: TagKey<Item> = itemTag(commonId("lava_buckets"))
        val COOKIES: TagKey<Item> = itemTag(commonId("cookies"))
        val CHEST_FEATURE_DISABLED: TagKey<Item> = itemTag(id("chest_feature_disabled"))
        val LEATHER_ITEMS: TagKey<Item> = itemTag(commonId("leather_items"))
        val LIGHT_EMITTERS: TagKey<Item> = itemTag(commonId("light_emitters"))
        val MALUM_GROSS_FOODS: TagKey<Item> = itemTag(ResourceLocation("malum", "gross_foods"))
        val CHEST_ARMOR_IGNORE: TagKey<Item> = itemTag(id("chest_armor_ignore"))
        val NON_RECOLORABLE: TagKey<Item> = itemTag(ResourceLocation("moonlight", "non_recolorable"))
        val MAGNET: TagKey<Item> = itemTag(ResourceLocation("create_new_age", "magnet"))
        val UPRIGHT_ON_BELT = itemTag(ResourceLocation("create", "upright_on_belt"))
        val DISABLES_CAPE = itemTag(modId("disables_cape"))
    }

    object Blocks {
        val PICKAXE_MINABLE: TagKey<Block> = blockTag(mcId("mineable/pickaxe"))
        val MAGNET_12: TagKey<Block> = blockTag(ResourceLocation("create_new_age", "magnets/force_12"))
        val NON_RECOLORABLE: TagKey<Block> = blockTag(ResourceLocation("moonlight", "non_recolorable"))
    }

    object Fluids {
        val WATER: TagKey<Fluid> = fluidTag(mcId("water"))
        val LAVA: TagKey<Fluid> = fluidTag(mcId("lava"))
        val URINE: TagKey<Fluid> = fluidTag(id("urine"))
        val PROCESSING_LAVA: TagKey<Fluid> = fluidTag(ResourceLocation("create", "fan_processing_catalysts/blasting"))
        val SPONGE_IGNORING: TagKey<Fluid> = fluidTag(id("sponge_ignoring"))
    }

    object Entities {
        val URINE_GIVING: TagKey<EntityType<*>> = entityTypeTag(id("urine_giving"))
    }

    object Biomes {
        val SPAWNS_MOTH: TagKey<Biome> = Registries.BIOME.tag(id("spawns_moth"))
    }
}