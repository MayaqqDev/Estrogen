package dev.mayaqq.estrogen.datagen.impl.tags

import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.datagen.platform.PlatformHelper
import dev.mayaqq.estrogen.datagen.tags.BaseTagProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture

class EstrogenItemTags(
    data: FabricDataOutput,
    completableFeature: CompletableFuture<HolderLookup.Provider>,
    helper: PlatformHelper
) : BaseTagProvider.ItemProvider(data, completableFeature, helper) {
    override fun addTags(provider: HolderLookup.Provider) {
        getOrCreateTagBuilder(EstrogenTags.Items.THIGHS)
            .add(EstrogenItems.EstrogenPatches)
            .add(EstrogenItems.ThighHighs)
        getOrCreateTagBuilder(EstrogenTags.Items.UWUFYING)
            .add(EstrogenItems.ColonThree)
        getOrCreateTagBuilder(EstrogenTags.Items.CURIOS_THIGHS)
            .add(EstrogenItems.EstrogenPatches)
            .add(EstrogenItems.ThighHighs)
        getOrCreateTagBuilder(EstrogenTags.Items.MUSIC_DISCS)
            .add(EstrogenItems.EstrogenChipCookie)
        getOrCreateTagBuilder(EstrogenTags.Items.LAVA_BUCKETS)
            .add(EstrogenFluids.MoltenSlime.bucket)
            .add(EstrogenFluids.MoltenAmethyst.bucket)
        getOrCreateTagBuilder(EstrogenTags.Items.COOKIES)
            .add(EstrogenItems.EstrogenChipCookie)
            .add(Items.COOKIE)
        getOrCreateTagBuilder(EstrogenTags.Items.LEATHER_ITEMS)
            .add(Items.LEATHER)
            .add(Items.LEATHER_BOOTS)
            .add(Items.LEATHER_CHESTPLATE)
            .add(Items.LEATHER_HELMET)
            .add(Items.LEATHER_LEGGINGS)
            .add(Items.LEATHER_HORSE_ARMOR)
        getOrCreateTagBuilder(EstrogenTags.Items.LIGHT_EMITTERS)
            .add(Items.TORCH)
            .add(Items.TORCHFLOWER)
            .add(Items.LANTERN)
            .add(Items.SOUL_LANTERN)
            .add(Items.CANDLE)
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.WOOL)
            .add(EstrogenBlocks.MothWool.asItem())
            .add(EstrogenBlocks.QuiltedMothWool.asItem())
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.WOOL_CARPETS)
            .add(EstrogenBlocks.MothCarpet.asItem())
            .add(EstrogenBlocks.QuiltedMothCarpet.asItem())
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.BEDS)
            .add(EstrogenBlocks.MothBed.asItem())
            .add(EstrogenBlocks.QuiltedMothBed.asItem())
        getOrCreateTagBuilder(EstrogenTags.Items.UPRIGHT_ON_BELT)
            .add(EstrogenItems.GenderChangePotion)
            .add(EstrogenItems.HorseUrineBottle)
        getOrCreateTagBuilder(EstrogenTags.Items.MALUM_GROSS_FOODS)
            .add(EstrogenItems.HorseUrineBottle)
        getOrCreateTagBuilder(EstrogenTags.Items.CHEST_ARMOR_IGNORE)
            .add(Items.ELYTRA)
            .add(EstrogenItems.MothElytra)
        getOrCreateTagBuilder(EstrogenTags.Items.NON_RECOLORABLE)
            .add(EstrogenBlocks.MothWool.asItem())
            .add(EstrogenBlocks.MothCarpet.asItem())
            .add(EstrogenBlocks.QuiltedMothWool.asItem())
            .add(EstrogenBlocks.QuiltedMothCarpet.asItem())
            .add(EstrogenBlocks.MothBed.asItem())
            .add(EstrogenBlocks.QuiltedMothBed.asItem())
        getOrCreateTagBuilder(EstrogenTags.Items.MAGNET)
            .add(EstrogenBlocks.DreamBlock.asItem())
    }
}