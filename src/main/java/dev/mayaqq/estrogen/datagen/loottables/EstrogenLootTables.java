package dev.mayaqq.estrogen.datagen.loottables;

import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class EstrogenLootTables extends FabricBlockLootTableProvider {

    public EstrogenLootTables(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(EstrogenBlocks.CENTRIFUGE.get(), EstrogenBlocks.CENTRIFUGE.get());
    }
}
