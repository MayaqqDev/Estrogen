package dev.mayaqq.estrogen.datagen.impl.loottables;

import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.Supplier;

public class EstrogenLootTables extends FabricBlockLootTableProvider {

    public EstrogenLootTables(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        add(EstrogenBlocks.CENTRIFUGE, createSingleItemTable(EstrogenBlocks.CENTRIFUGE));
        add(EstrogenBlocks.COOKIE_JAR, createSilkTouchOnlyTable(EstrogenBlocks.COOKIE_JAR));
        add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get(), createSingleItemTable(EstrogenBlocks.DORMANT_DREAM_BLOCK));
        add(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get(), createSingleItemTable(EstrogenBlocks.ESTROGEN_PILL_BLOCK));
        add(EstrogenBlocks.MOTH_WOOL.get(), createSingleItemTable(EstrogenBlocks.MOTH_WOOL));
        add(EstrogenBlocks.MOTH_SEAT.get(), createSingleItemTable(EstrogenBlocks.MOTH_SEAT));
        add(EstrogenBlocks.QUILTED_MOTH_WOOL.get(), createSingleItemTable(EstrogenBlocks.QUILTED_MOTH_WOOL));
        add(EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET.get(), createSingleItemTable(EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET));
        add(EstrogenBlocks.MOTH_WOOL_CARPET.get(), createSingleItemTable(EstrogenBlocks.MOTH_WOOL_CARPET));
        add(EstrogenBlocks.MOTH_BED.get(), createSingleItemTable(EstrogenBlocks.MOTH_BED));
        add(EstrogenBlocks.QUILTED_MOTH_BED.get(), createSingleItemTable(EstrogenBlocks.QUILTED_MOTH_BED));
    }

    private void add(Supplier<? extends Block> block, LootTable.Builder factory) {
        this.add(block.get(), factory);
    }
}
