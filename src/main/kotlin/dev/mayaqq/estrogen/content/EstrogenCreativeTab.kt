package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenBlocks.CookieJar
import dev.mayaqq.estrogen.content.EstrogenBlocks.DreamBlock
import dev.mayaqq.estrogen.content.EstrogenBlocks.DreamCatcher
import dev.mayaqq.estrogen.content.EstrogenBlocks.EstrogenPillBlock
import dev.mayaqq.estrogen.content.EstrogenBlocks.MothBed
import dev.mayaqq.estrogen.content.EstrogenBlocks.MothCarpet
import dev.mayaqq.estrogen.content.EstrogenBlocks.MothWool
import dev.mayaqq.estrogen.content.EstrogenBlocks.QuiltedMothBed
import dev.mayaqq.estrogen.content.EstrogenBlocks.QuiltedMothCarpet
import dev.mayaqq.estrogen.content.EstrogenBlocks.QuiltedMothWool
import dev.mayaqq.estrogen.content.EstrogenItems.Balls
import dev.mayaqq.estrogen.content.EstrogenItems.ColonThree
import dev.mayaqq.estrogen.content.EstrogenItems.CrystalEstrogenPill
import dev.mayaqq.estrogen.content.EstrogenItems.EstrogenChipCookie
import dev.mayaqq.estrogen.content.EstrogenItems.EstrogenPill
import dev.mayaqq.estrogen.content.EstrogenItems.GenderChangePotion
import dev.mayaqq.estrogen.content.EstrogenItems.HorseUrineBottle
import dev.mayaqq.estrogen.content.EstrogenItems.MothElytra
import dev.mayaqq.estrogen.content.EstrogenItems.MothFuzz
import dev.mayaqq.estrogen.content.EstrogenItems.TestosteroneChunk
import dev.mayaqq.estrogen.content.EstrogenItems.TestosteronePowder
import dev.mayaqq.estrogen.content.EstrogenItems.ThighHighs
import dev.mayaqq.estrogen.id
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionUtils
import uwu.serenity.kritter.api.Registrar
import uwu.serenity.kritter.api.entry.RegistryEntry
import uwu.serenity.kritter.stdlib.creativeTab


object EstrogenCreativeTab : Registrar<CreativeModeTab> by Estrogen..Registries.CREATIVE_MODE_TAB {

    // Make it an entry here
    val Estrogen: RegistryEntry<CreativeModeTab> = creativeTab("estrogen") {
        title = Component.translatable("itemGroup.estrogen.estrogen")
        icon { EstrogenPill.defaultInstance }
        displayItems { 
            accept(EstrogenPill)
            accept(CrystalEstrogenPill)
            accept(GenderChangePotion)
            accept(Balls)
            accept(MothFuzz)
            accept(TestosteroneChunk)
            accept(TestosteronePowder)
            accept(EstrogenChipCookie)
            accept(HorseUrineBottle)
            //accept(ESTROGEN_PATCHES.get().getFullStack())
            //accept(ESTROGEN_PATCHES)
            accept(ThighHighs)
            accept(MothElytra)
            accept(ColonThree)
            accept(CookieJar)
            accept(DreamCatcher)
            accept(DreamBlock)
            accept(EstrogenPillBlock)
            accept(MothWool)
            accept(QuiltedMothWool)
            accept(MothCarpet)
            accept(QuiltedMothCarpet)
            accept(MothBed)
            accept(QuiltedMothBed)
            accept(tippedArrow(EstrogenPotions.EstrogenPotion))
//            accept(MOLTEN_SLIME.getBucket())
//            accept(TESTOSTERONE_MIXTURE.getBucket())
//            accept(LIQUID_ESTROGEN.getBucket())
//            accept(FILTRATED_HORSE_URINE.getBucket())
//            accept(HORSE_URINE.getBucket())
//            accept(MOLTEN_AMETHYST.getBucket())
//            accept(GENDER_FLUID.getBucket())
            accept(BuiltInRegistries.ITEM.get(id("moth_spawn_egg")))
            ThighHighs.styleItems.forEach(::accept)
        }
    }
}

private fun tippedArrow(potion: Potion): ItemStack = Items.TIPPED_ARROW.defaultInstance.also { PotionUtils.setPotion(it, potion) }
