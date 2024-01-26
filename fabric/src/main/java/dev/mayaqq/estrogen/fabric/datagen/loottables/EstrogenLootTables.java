package dev.mayaqq.estrogen.fabric.datagen.loottables;

import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class EstrogenLootTables extends FabricBlockLootTableProvider {

    public EstrogenLootTables(FabricDataGenerator dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateBlockLootTables() {
        add(EstrogenBlocks.CENTRIFUGE.get(), createSingleItemTable(EstrogenBlocks.CENTRIFUGE.get()));
    }
}
