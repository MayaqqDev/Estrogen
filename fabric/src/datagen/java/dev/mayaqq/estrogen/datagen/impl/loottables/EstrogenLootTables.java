package dev.mayaqq.estrogen.datagen.impl.loottables;

import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.blocks.ModelBedBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class EstrogenLootTables extends FabricBlockLootTableProvider {

    public EstrogenLootTables(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        add(EstrogenBlocks.CENTRIFUGE.get(), createSingleItemTable(EstrogenBlocks.CENTRIFUGE.get()));
        add(EstrogenBlocks.COOKIE_JAR.get(), createSilkTouchOnlyTable(EstrogenBlocks.COOKIE_JAR.get()));
        add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get(), createSingleItemTable(EstrogenBlocks.DORMANT_DREAM_BLOCK.get()));
        add(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get(), createSingleItemTable(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get()));
        add(EstrogenBlocks.MOTH_WOOL.get(), createSingleItemTable(EstrogenBlocks.MOTH_WOOL.get()));
        add(EstrogenBlocks.MOTH_SEAT.get(), createSingleItemTable(EstrogenBlocks.MOTH_SEAT.get()));
        add(EstrogenBlocks.QUILTED_MOTH_WOOL.get(), createSingleItemTable(EstrogenBlocks.QUILTED_MOTH_WOOL.get()));
        add(EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET.get(), createSingleItemTable(EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET.get()));
        add(EstrogenBlocks.MOTH_WOOL_CARPET.get(), createSingleItemTable(EstrogenBlocks.MOTH_WOOL_CARPET.get()));
        add(EstrogenBlocks.MOTH_BED.get(), createSinglePropConditionTable(EstrogenBlocks.MOTH_BED.get(), ModelBedBlock.PART, BedPart.HEAD));
        add(EstrogenBlocks.QUILTED_MOTH_BED.get(), createSinglePropConditionTable(EstrogenBlocks.QUILTED_MOTH_BED.get(), ModelBedBlock.PART, BedPart.HEAD));
    }
}
