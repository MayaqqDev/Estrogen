package dev.mayaqq.estrogen.datagen.impl.loottables

import dev.mayaqq.estrogen.content.EstrogenBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.world.level.block.BedBlock
import net.minecraft.world.level.block.state.properties.BedPart
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider


class EstrogenLoottables(output: FabricDataOutput) : FabricBlockLootTableProvider(output) {

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
        add(EstrogenBlocks.DreamCatcher, createSingleItemTable(EstrogenBlocks.DreamCatcher).apply(
            CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                .copy(
                "colors",
                "colors"
            )
        ))
    }
}