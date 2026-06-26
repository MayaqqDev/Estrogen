package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.core.identifier
import dev.mayaqq.cynosure.items.extensions.CustomTooltip
import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.client.content.baubles.EstrogenPatchesRenderer
import dev.mayaqq.estrogen.client.content.baubles.ThighHighsRenderer
import dev.mayaqq.estrogen.config.EstrogenCommonConfig
import dev.mayaqq.estrogen.content.items.*
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.creativeTabs.TabPlacement
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.entry.key
import invoke.kitty.kritter.registry.item.creativeTab
import invoke.kitty.kritter.registry.item.item
import net.minecraft.ChatFormatting
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*

object EstrogenItems : Registrar<Item> by Registrar(MOD_ID, Registries.ITEM) {
    val EstrogenPill by item("estrogen_pill", ::Item) {
        properties {
            stacksTo(16)
            food(FoodProperties.Builder().effect(
                MobEffectInstance(
                    EstrogenEffects.Estrogen.holder(),
                    EstrogenCommonConfig.Durations.estrogenPillDuration,
                    0,
                    false,
                    false,
                    true
                ), 1F)
                .fast().alwaysEdible().build()
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
                    EstrogenEffects.Estrogen.holder(),
                    EstrogenCommonConfig.Durations.crystalEstrogenPillDuration,
                    1,
                    false,
                    false,
                    true
                ), 1F)
                .fast().alwaysEdible().build()
            )
            rarity(Rarity.EPIC)
        }
        standardTooltip()
    }

    val Balls by item("balls", ::Item)

    val TestosteroneChunk by item("testosterone_chunk", ::Item)
    val TestosteronePowder by item("testosterone_powder", ::Item)
    val MothFuzz by item("moth_fuzz", ::Item) {
        creativeTab(CreativeModeTabs.INGREDIENTS, TabPlacement.AFTER(Items.INK_SAC))
    }

    val EstrogenChipCookie by item("estrogen_chip_cookie", ::Item) {
        properties {
            rarity(Rarity.RARE)
            jukeboxPlayable(EstrogenRecordSongs.G03C.key)
            food(
                FoodProperties.Builder().effect(MobEffectInstance(
                    EstrogenEffects.Estrogen.holder(),
                    EstrogenCommonConfig.Durations.estrogenChipCookieDuration,
                    0,
                    false,
                    false,
                    true
                ), 1F).nutrition(8).saturationModifier(1.5F).fast().alwaysEdible().build()
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
                ).nutrition(1).saturationModifier(0.1f).build()
            )
            craftRemainder(Items.GLASS_BOTTLE)
        }
    }

    val ColonThree by item("uwu", ::Item) {
        properties {
            stacksTo(1)
        }
        tooltip {
            CustomTooltip { stack, player, flags -> add(Component.literal("UwU").withStyle(ChatFormatting.LIGHT_PURPLE)) }
        }
    }

    val ThighHighs by item("thigh_highs", { p -> ThighHighsItem(p, 0xf1d85a, 0xff4ea5)}) {
        properties {
            stacksTo(1)
        }
        standardTooltip()
        //TODO: baubleWithRenderer(::ThighHighsRenderer)
        //TODO: color(ThighHighsItem::getItemColor)
        onSetup { CauldronInteraction.WATER.map()[it] = ThighHighsItem.CAULDRON_INTERACTION }
    }

    val EstrogenPatches by item("estrogen_patches", ::EstrogenPatchesItem) {
        properties {
            stacksTo(1)
        }
        //TODO: baubleWithRenderer(::EstrogenPatchesRenderer)
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
                    entity.attributes.hasAttribute(EstrogenAttributes.ShowBoobs.holder()) &&
                    entity.getAttributeValue(EstrogenAttributes.ShowBoobs.holder()) != 0.0
                ) 1.0f else 0.0f
        }
        creativeTab(CreativeModeTabs.FOOD_AND_DRINKS, TabPlacement.AFTER(Items.HONEY_BOTTLE))
    }

    lateinit var DreamBottle: DreamBottleItem
        internal set
}