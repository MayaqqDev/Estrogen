package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.content.items.*
import dev.mayaqq.estrogen.id
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.creative.TabPlacement
import uwu.serenity.kritter.stdlib.item

object EstrogenItems : Registrar<Item> by Estrogen..Registries.ITEM {
    val EstrogenPill by item("estrogen_pill", ::Item) {
        properties {
            stacksTo(16)
            food(FoodProperties.Builder().effect(
                MobEffectInstance(
                    EstrogenEffects.Estrogen,
                    EstrogenCommonConfig.Durations.estrogenPillDuration,
                    0,
                    false,
                    false,
                    true
                ), 1F)
                .fast().alwaysEat().build()
            )
            rarity(Rarity.RARE)
        }
        standardTooltip()
    }

    val CrystalEstrogenPill by item("crystal_estrogen_pill", ::Item) {
        properties {
            stacksTo(16)
            food(FoodProperties.Builder().effect(
                MobEffectInstance(
                    EstrogenEffects.Estrogen,
                    EstrogenCommonConfig.Durations.crystalEstrogenPillDuration,
                    1,
                    false,
                    false,
                    true
                ), 1F)
                .fast().alwaysEat().build()
            )
            rarity(Rarity.EPIC)
        }
        standardTooltip()
    }

    val Balls by item("balls", ::Item)

    val TestosteroneChunk by item("testosterone_chunk", ::Item)
    val TestosteronePowder by item("testosterone_powder", ::Item)
    //TODO: Create version only val USED_FILTER
    val MothFuzz by item("moth_fuzz", ::Item) {
        creativeTab(CreativeModeTabs.INGREDIENTS, TabPlacement.AFTER(Items.INK_SAC))
    }

    val EstrogenChipCookie by item("estrogen_chip_cookie", ::EstrogenCookieItem) {
        properties {
            rarity(Rarity.RARE)
            food(
                FoodProperties.Builder().effect(MobEffectInstance(
                    EstrogenEffects.Estrogen,
                    EstrogenCommonConfig.Durations.estrogenChipCookieDuration,
                    0,
                    false,
                    false,
                    true
                ), 1F).nutrition(8).saturationMod(1.5F).fast().alwaysEat().build()
            )
            stacksTo(64)
        }
        creativeTab(CreativeModeTabs.FOOD_AND_DRINKS, TabPlacement.AFTER(Items.COOKIE))
    }

    val HorseUrineBottle by item("horse_urine_bottle", ::HorseUrineBottleItem) {
        properties {
            stacksTo(16)
            food(
                FoodProperties.Builder().effect(
                    MobEffectInstance(
                        MobEffects.POISON,
                        100,
                        0
                    ), 1f
                ).nutrition(1).saturationMod(0.1f).build()
            )
            craftRemainder(Items.GLASS_BOTTLE)
        }
    }

    //TODO: Incomplete Estrogen Patch FOR CREATE ESTROGEN

    val ColonThree by item("uwu", ::Item) {
        properties {
            stacksTo(1)
        }
        /* TODO:
        tooltip {
            TODO("TOOLTIP hehe")
        }
         */
    }

    //TODO: Incomplete Colonthree FOR CREATE ESTROGEN

    val ThighHighs by item("thigh_highs", { p -> ThighHighsItem(p, 0xf1d85a, 0xff4ea5)}) {
        properties {
            stacksTo(1)
        }
        /* TODO:
        tooltip {
            TODO("THIGHHIGHSTOOLTIPMODIFIER")
        }
         */
        bauble()
        //TODO: baubleWithRenderer {  }
        onSetup { CauldronInteraction.WATER[it] = ThighHighsItem.CAULDRON_INTERACTION }
        color(ThighHighsItem::getItemColor)
    }

    val EstrogenPatches by item("estrogen_patches", ::EstrogenPatchesItem) {
        properties {
            stacksTo(1)
        }
        bauble()
        //TODO: baubleWithRenderer {  }
    }

    val MothElytra by item("moth_elytra", ::MothElytraItem) {
        properties {
            stacksTo(1)
            durability(626)
            rarity(Rarity.UNCOMMON)
        }
        creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES, TabPlacement.AFTER(Items.ELYTRA))

    }

    val GenderChangePotion by item("gender_change_potion", ::GenderChangePotionItem) {
        properties {
            stacksTo(1)
            rarity(Rarity.RARE)
        }
        textureProperty(id("gender")) { _, _, entity, _ ->
            return@textureProperty if(
                    entity != null &&
                    entity.attributes.hasAttribute(EstrogenAttributes.ShowBoobs) &&
                    entity.getAttributeValue(EstrogenAttributes.ShowBoobs) != 0.0
                ) 1.0f else 0.0f
        }
        creativeTab(CreativeModeTabs.FOOD_AND_DRINKS, TabPlacement.AFTER(Items.HONEY_BOTTLE))
    }

    lateinit var DreamBottle: DreamBottleItem
        internal set
}