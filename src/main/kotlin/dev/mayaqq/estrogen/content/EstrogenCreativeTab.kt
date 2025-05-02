package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.CreativeModeTab
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.stdlib.creativeTab
import dev.mayaqq.estrogen.content.EstrogenItems.ESTROGEN_PILL
import dev.mayaqq.estrogen.content.EstrogenItems.CRYSTAL_ESTROGEN_PILL
import dev.mayaqq.estrogen.content.EstrogenItems.GENDER_CHANGE_POTION
import dev.mayaqq.estrogen.content.EstrogenItems.BALLS
import dev.mayaqq.estrogen.content.EstrogenItems.MOTH_FUZZ
import dev.mayaqq.estrogen.content.EstrogenItems.TESTOSTERONE_CHUNK
import dev.mayaqq.estrogen.content.EstrogenItems.TESTOSTERONE_POWDER
import dev.mayaqq.estrogen.content.EstrogenItems.ESTROGEN_CHIP_COOKIE
import dev.mayaqq.estrogen.content.EstrogenItems.HORSE_URINE_BOTTLE
import dev.mayaqq.estrogen.content.EstrogenItems.THIGH_HIGHS
import dev.mayaqq.estrogen.content.EstrogenItems.MOTH_ELYTRA
import dev.mayaqq.estrogen.content.EstrogenItems.COLONTHREE
import dev.mayaqq.estrogen.content.EstrogenBlocks.COOKIE_JAR
import dev.mayaqq.estrogen.content.EstrogenBlocks.DREAM_BLOCK
import dev.mayaqq.estrogen.content.EstrogenBlocks.DORMANT_DREAM_BLOCK
import dev.mayaqq.estrogen.content.EstrogenBlocks.ESTROGEN_PILL_BLOCK
import dev.mayaqq.estrogen.content.EstrogenBlocks.MOTH_WOOL
import dev.mayaqq.estrogen.content.EstrogenBlocks.QUILTED_MOTH_WOOL
import dev.mayaqq.estrogen.content.EstrogenBlocks.QUILTED_MOTH_BED
import dev.mayaqq.estrogen.content.EstrogenBlocks.QUILTED_MOTH_CARPET
import dev.mayaqq.estrogen.content.EstrogenBlocks.MOTH_CARPET
import dev.mayaqq.estrogen.content.EstrogenBlocks.MOTH_BED
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionUtils
import uwu.serenity.kritter.api.entry.RegistryEntry


object EstrogenCreativeTab : Registrar<CreativeModeTab> by Estrogen..Registries.CREATIVE_MODE_TAB {

    // Make it an entry here
    val ESTROGEN: RegistryEntry<CreativeModeTab> = creativeTab("estrogen") {
        icon { ESTROGEN_PILL.defaultInstance }
        displayItems { 
            accept(ESTROGEN_PILL);
            accept(CRYSTAL_ESTROGEN_PILL);
            accept(GENDER_CHANGE_POTION);
            accept(BALLS);
            accept(MOTH_FUZZ);
            accept(TESTOSTERONE_CHUNK);
            accept(TESTOSTERONE_POWDER);
            accept(ESTROGEN_CHIP_COOKIE);
            accept(HORSE_URINE_BOTTLE);
            //accept(ESTROGEN_PATCHES.get().getFullStack());
            //accept(ESTROGEN_PATCHES);
            accept(THIGH_HIGHS);
            accept(MOTH_ELYTRA);
            accept(COLONTHREE);
            accept(COOKIE_JAR);
            accept(DREAM_BLOCK);
            accept(ESTROGEN_PILL_BLOCK);
            accept(MOTH_WOOL);
            accept(QUILTED_MOTH_WOOL);
            accept(MOTH_CARPET);
            accept(QUILTED_MOTH_CARPET);
            accept(MOTH_BED);
            accept(QUILTED_MOTH_BED);
            accept(tippedArrow(EstrogenPotions.ESTROGEN_POTION));
//            accept(MOLTEN_SLIME.getBucket());
//            accept(TESTOSTERONE_MIXTURE.getBucket());
//            accept(LIQUID_ESTROGEN.getBucket());
//            accept(FILTRATED_HORSE_URINE.getBucket());
//            accept(HORSE_URINE.getBucket());
//            accept(MOLTEN_AMETHYST.getBucket());
//            accept(GENDER_FLUID.getBucket());
//            accept(MOTH.getSpawnEgg());
            THIGH_HIGHS.styleItems.forEach(::accept)
        }
    }
}

private fun tippedArrow(potion: Potion): ItemStack = Items.POTION.defaultInstance.also { PotionUtils.setPotion(it, potion) }
