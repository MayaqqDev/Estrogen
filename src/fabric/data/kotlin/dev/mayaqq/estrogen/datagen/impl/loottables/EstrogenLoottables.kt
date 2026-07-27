package dev.mayaqq.estrogen.datagen.impl.loottables

import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenComponents
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.level.block.BedBlock
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction
import java.util.concurrent.CompletableFuture


class EstrogenLoottables(output: FabricDataOutput, lookup: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootTableProvider(output, lookup)
{

    override fun generate() {
        add(EstrogenBlocks.CookieJar.value!!, createSilkTouchOnlyTable(EstrogenBlocks.CookieJar.value!!))
        add(EstrogenBlocks.DreamBlock.get(), createSingleItemTable(EstrogenBlocks.DreamBlock.get()))
        add(EstrogenBlocks.EstrogenPillBlock.value!!, createSingleItemTable(EstrogenBlocks.EstrogenPillBlock.value!!))
        add(EstrogenBlocks.MothWool.value!!, createSingleItemTable(EstrogenBlocks.MothWool.value!!))
        add(EstrogenBlocks.QuiltedMothWool.value!!, createSingleItemTable(EstrogenBlocks.QuiltedMothWool.value!!))
        add(EstrogenBlocks.QuiltedMothCarpet.value!!, createSingleItemTable(EstrogenBlocks.QuiltedMothCarpet.value!!))
        add(EstrogenBlocks.MothCarpet.value!!, createSingleItemTable(EstrogenBlocks.MothCarpet.value!!))
        add(
            EstrogenBlocks.MothBed.value!!,
            createSinglePropConditionTable(EstrogenBlocks.MothBed.value!!, BedBlock.PART, BedPart.HEAD)
        )
        add(
            EstrogenBlocks.QuiltedMothBed.value!!,
            createSinglePropConditionTable(EstrogenBlocks.QuiltedMothBed.value!!, BedBlock.PART, BedPart.HEAD)
        )
        add(EstrogenBlocks.DreamCatcher.value!!, createSingleItemTable(EstrogenBlocks.DreamCatcher.value!!).apply(
            CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(
                EstrogenComponents.TriColorComponent
            )
        ))
    }
}