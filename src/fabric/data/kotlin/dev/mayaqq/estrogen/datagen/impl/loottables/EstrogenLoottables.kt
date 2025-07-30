package dev.mayaqq.estrogen.datagen.impl.loottables

import dev.mayaqq.estrogen.content.EstrogenBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.world.level.block.BedBlock
import net.minecraft.world.level.block.state.properties.BedPart


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
            createSinglePropConditionTable<BedPart?>(EstrogenBlocks.MothBed, BedBlock.PART, BedPart.HEAD)
        )
        add(
            EstrogenBlocks.QuiltedMothBed,
            createSinglePropConditionTable<BedPart?>(EstrogenBlocks.QuiltedMothBed, BedBlock.PART, BedPart.HEAD)
        )
        add(EstrogenBlocks.DreamCatcher, createSingleItemTable(EstrogenBlocks.DreamCatcher))
    }
}