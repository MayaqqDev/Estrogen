package dev.mayaqq.estrogen.datagen.impl.loottables

import dev.mayaqq.estrogen.content.EstrogenBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.world.level.block.BedBlock
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider
import java.util.concurrent.CompletableFuture


class EstrogenLoottables(output: FabricDataOutput, lookup: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootTableProvider(output, lookup)
{

    override fun generate() {
        add(EstrogenBlocks.CookieJar, createSilkTouchOnlyTable(EstrogenBlocks.CookieJar))
        add(EstrogenBlocks.DreamBlock, createSingleItemTable(EstrogenBlocks.DreamBlock))
        add(EstrogenBlocks.EstrogenPillBlock, createSingleItemTable(EstrogenBlocks.EstrogenPillBlock))
        add(EstrogenBlocks.MothWool, createSingleItemTable(EstrogenBlocks.MothWool))
        add(EstrogenBlocks.QuiltedMothWool, createSingleItemTable(EstrogenBlocks.QuiltedMothWool))
        add(EstrogenBlocks.QuiltedMothCarpet, createSingleItemTable(EstrogenBlocks.QuiltedMothCarpet))
        add(EstrogenBlocks.MothCarpet, createSingleItemTable(EstrogenBlocks.MothCarpet))
        add(
            EstrogenBlocks.MothBed,
            createSinglePropConditionTable(EstrogenBlocks.MothBed, BedBlock.PART, BedPart.HEAD)
        )
        add(
            EstrogenBlocks.QuiltedMothBed,
            createSinglePropConditionTable(EstrogenBlocks.QuiltedMothBed, BedBlock.PART, BedPart.HEAD)
        )
        //TODO: check on this
        add(EstrogenBlocks.DreamCatcher, createSingleItemTable(EstrogenBlocks.DreamCatcher).apply(
            CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY).include(
                DataComponents.BLOCK_ENTITY_DATA
            )
        ))
    }
}