package dev.mayaqq.estrogen.content

import dev.mayaqq.estrogen.MOD_ID
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
import dev.mayaqq.estrogen.content.EstrogenFluids.FiltratedHorseUrine
import dev.mayaqq.estrogen.content.EstrogenFluids.GenderFluid
import dev.mayaqq.estrogen.content.EstrogenFluids.HorseUrine
import dev.mayaqq.estrogen.content.EstrogenFluids.LiquidEstrogen
import dev.mayaqq.estrogen.content.EstrogenFluids.MoltenAmethyst
import dev.mayaqq.estrogen.content.EstrogenFluids.MoltenSlime
import dev.mayaqq.estrogen.content.EstrogenFluids.TestosteroneMixture
import dev.mayaqq.estrogen.content.EstrogenItems.Balls
import dev.mayaqq.estrogen.content.EstrogenItems.ColonThree
import dev.mayaqq.estrogen.content.EstrogenItems.CrystalEstrogenPill
import dev.mayaqq.estrogen.content.EstrogenItems.DreamBottle
import dev.mayaqq.estrogen.content.EstrogenItems.EstrogenChipCookie
import dev.mayaqq.estrogen.content.EstrogenItems.EstrogenPatches
import dev.mayaqq.estrogen.content.EstrogenItems.EstrogenPill
import dev.mayaqq.estrogen.content.EstrogenItems.GenderChangePotion
import dev.mayaqq.estrogen.content.EstrogenItems.HorseUrineBottle
import dev.mayaqq.estrogen.content.EstrogenItems.MothElytra
import dev.mayaqq.estrogen.content.EstrogenItems.MothFuzz
import dev.mayaqq.estrogen.content.EstrogenItems.TestosteroneChunk
import dev.mayaqq.estrogen.content.EstrogenItems.TestosteronePowder
import dev.mayaqq.estrogen.content.EstrogenItems.ThighHighs
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.defaultInstance
import dev.mayaqq.estrogen.utils.holder
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import invoke.kitty.kritter.registry.creativeTab.creativeTab
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.block.Block


object EstrogenCreativeTab : Registrar<CreativeModeTab> by Registrar(MOD_ID, Registries.CREATIVE_MODE_TAB) {

    // Make it an entry here
    val Estrogen = creativeTab("estrogen") {
        title = Component.translatable("itemGroup.estrogen.estrogen")
        icon { EstrogenPill.defaultInstance() }
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
            accept(EstrogenPatches.value!!.getFullStack())
            accept(EstrogenPatches)
            accept(ThighHighs)
            accept(MothElytra)
            accept(ColonThree)
            accept(CookieJar)
            accept(DreamCatcher)
            accept(DreamBlock.value!!.asItem())
            accept(DreamBottle)
            accept(EstrogenPillBlock)
            accept(MothWool)
            accept(QuiltedMothWool)
            accept(MothCarpet)
            accept(QuiltedMothCarpet)
            accept(MothBed)
            accept(QuiltedMothBed)
            accept(tippedArrow(EstrogenPotions.EstrogenPotion.holder()))
            accept(MoltenSlime.bucket)
            accept(TestosteroneMixture.bucket)
            accept(LiquidEstrogen.bucket)
            accept(FiltratedHorseUrine.bucket)
            accept(HorseUrine.bucket)
            accept(MoltenAmethyst.bucket)
            accept(GenderFluid.bucket)
            accept(BuiltInRegistries.ITEM.get(id("moth_spawn_egg")))
            ThighHighs.value!!.styleItems.forEach(::accept)
        }
    }
}

private fun <T : Block> CreativeModeTab.Output.accept(holder: RegistryEntry<T>) = this.accept(holder.value!!)

private fun tippedArrow(potion: Holder<Potion>): ItemStack = PotionContents.createItemStack(Items.TIPPED_ARROW, potion)
