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
            .add(EstrogenBlocks.CookieJar)
            .add(EstrogenBlocks.DreamBlock)
            .add(EstrogenBlocks.EstrogenPillBlock)
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
            .add(EstrogenBlocks.MothBed)
            .add(EstrogenBlocks.QuiltedMothBed)
            .add(EstrogenBlocks.DreamCatcher)
        getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12)
            .add(EstrogenBlocks.DreamBlock)
        getOrCreateTagBuilder(BlockTags.WOOL)
            .add(EstrogenBlocks.MothWool)
            .add(EstrogenBlocks.QuiltedMothWool)
        getOrCreateTagBuilder(BlockTags.WOOL_CARPETS)
            .add(EstrogenBlocks.MothCarpet)
            .add(EstrogenBlocks.QuiltedMothCarpet)
        getOrCreateTagBuilder(BlockTags.BEDS)
            .add(EstrogenBlocks.MothBed)
            .add(EstrogenBlocks.QuiltedMothBed)
        getOrCreateTagBuilder(EstrogenTags.Blocks.NON_RECOLORABLE)
            .add(EstrogenBlocks.MothWool)
            .add(EstrogenBlocks.MothCarpet)
            .add(EstrogenBlocks.QuiltedMothWool)
            .add(EstrogenBlocks.QuiltedMothCarpet)
            .add(EstrogenBlocks.MothBed)
            .add(EstrogenBlocks.QuiltedMothBed)
    }
}