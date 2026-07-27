package dev.mayaqq.estrogen.content

import dev.mayaqq.cynosure.text.TextProperties.stripped
import dev.mayaqq.estrogen.Estrogen
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
import invoke.kitty.kritter.registry.api.Registrar
import invoke.kitty.kritter.registry.api.entry.RegistryEntry
import invoke.kitty.kritter.registry.api.entry.holder
import invoke.kitty.kritter.registry.block.item
import invoke.kitty.kritter.registry.creativeTab.creativeTab
import invoke.kitty.kritter.utils.extensions.asStack
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.alchemy.PotionContents
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block


object EstrogenCreativeTab : Registrar<CreativeModeTab> by Registrar(MOD_ID, Registries.CREATIVE_MODE_TAB) {

    // Make it an entry here
    val Estrogen = creativeTab("estrogen") {
        title = Component.translatable("itemGroup.estrogen.estrogen")
        icon(EstrogenPill::asStack)
        displayItems {
            acceptWithCount(EstrogenPill)
            acceptWithCount(CrystalEstrogenPill)
            acceptWithCount(GenderChangePotion)
            acceptWithCount(Balls)
            acceptWithCount(MothFuzz)
            acceptWithCount(TestosteroneChunk)
            acceptWithCount(TestosteronePowder)
            acceptWithCount(EstrogenChipCookie)
            acceptWithCount(HorseUrineBottle)
            acceptWithCount(EstrogenPatches.get().getFullStack())
            acceptWithCount(EstrogenPatches)
            acceptWithCount(ThighHighs)
            acceptWithCount(MothElytra)
            acceptWithCount(ColonThree)
            acceptWithCount(CookieJar)
            acceptWithCount(DreamCatcher)
            acceptWithCount(DreamBlock.asStack())
            acceptWithCount(DreamBottle)
            acceptWithCount(EstrogenPillBlock)
            acceptWithCount(MothWool)
            acceptWithCount(QuiltedMothWool)
            acceptWithCount(MothCarpet)
            acceptWithCount(QuiltedMothCarpet)
            acceptWithCount(MothBed)
            acceptWithCount(QuiltedMothBed)
            acceptWithCount(tippedArrow(EstrogenPotions.EstrogenPotion))
            acceptWithCount(MoltenSlime.bucket)
            acceptWithCount(TestosteroneMixture.bucket)
            acceptWithCount(LiquidEstrogen.bucket)
            acceptWithCount(FiltratedHorseUrine.bucket)
            acceptWithCount(HorseUrine.bucket)
            acceptWithCount(MoltenAmethyst.bucket)
            acceptWithCount(GenderFluid.bucket)
            ThighHighs.get().styleItems.forEach(::acceptWithCount)
        }
    }
}

private fun <T : ItemLike> CreativeModeTab.Output.acceptWithCount(holder: RegistryEntry<T>) = acceptWithCount(holder.get().asStack())

private fun CreativeModeTab.Output.acceptWithCount(stack: ItemStack) = accept(stack.apply { count = 1 })

private fun CreativeModeTab.Output.acceptWithCount(item: Item) = acceptWithCount(item.defaultInstance)

private fun tippedArrow(potion: RegistryEntry<Potion>): ItemStack = PotionContents.createItemStack(Items.TIPPED_ARROW, potion.holder).apply { count = 1 }
