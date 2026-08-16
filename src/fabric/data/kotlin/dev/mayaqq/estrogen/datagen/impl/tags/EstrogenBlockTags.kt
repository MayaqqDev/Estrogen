package dev.mayaqq.estrogen.datagen.impl.tags

import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenTags
import dev.mayaqq.estrogen.datagen.api.platform.PlatformHelper
import dev.mayaqq.estrogen.datagen.api.tags.BaseTagProvider
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture

class EstrogenBlockTags(
    data: FabricDataOutput,
    completableFeature: CompletableFuture<HolderLookup.Provider>,
    helper: PlatformHelper
) : BaseTagProvider.BlockProvider(data, completableFeature, helper) {
    override fun addTags(provider: HolderLookup.Provider) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(EstrogenBlocks.CookieJar.value!!)
            .add(EstrogenBlocks.DreamBlock.value!!)
            .add(EstrogenBlocks.EstrogenPillBlock.value!!)
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
            .add(EstrogenBlocks.MothBed.value!!)
            .add(EstrogenBlocks.QuiltedMothBed.value!!)
            .add(EstrogenBlocks.DreamCatcher.value!!)
        getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12)
            .add(EstrogenBlocks.DreamBlock.value!!)
        getOrCreateTagBuilder(BlockTags.WOOL)
            .add(EstrogenBlocks.MothWool.value!!)
            .add(EstrogenBlocks.QuiltedMothWool.value!!)
        getOrCreateTagBuilder(BlockTags.WOOL_CARPETS)
            .add(EstrogenBlocks.MothCarpet.value!!)
            .add(EstrogenBlocks.QuiltedMothCarpet.value!!)
        getOrCreateTagBuilder(BlockTags.BEDS)
            .add(EstrogenBlocks.MothBed.value!!)
            .add(EstrogenBlocks.QuiltedMothBed.value!!)
        getOrCreateTagBuilder(EstrogenTags.Blocks.NON_RECOLORABLE)
            .add(EstrogenBlocks.MothWool.value!!)
            .add(EstrogenBlocks.MothCarpet.value!!)
            .add(EstrogenBlocks.QuiltedMothWool.value!!)
            .add(EstrogenBlocks.QuiltedMothCarpet.value!!)
            .add(EstrogenBlocks.MothBed.value!!)
            .add(EstrogenBlocks.QuiltedMothBed.value!!)
        getOrCreateTagBuilder(EstrogenTags.Blocks.HIDDEN_FROM_RECIPE_VIEWERS)
            .add(EstrogenBlocks.ColonThreeBlock.get())
        getOrCreateTagBuilder(BlockTags.CAULDRONS)
            .add(EstrogenBlocks.HorseUrineCauldron.get())
            .add(EstrogenBlocks.FiltratedHorseUrineCauldron.get())
            .add(EstrogenBlocks.LiquidEstrogenCauldron.get())
    }
}