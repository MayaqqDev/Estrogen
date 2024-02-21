package dev.mayaqq.estrogen.datagen.loottables;

import dev.mayaqq.estrogen.registry.EstrogenCreateBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class EstrogenLootTables extends FabricBlockLootTableProvider {

    public EstrogenLootTables(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        add(EstrogenCreateBlocks.CENTRIFUGE.get(), createSingleItemTable(EstrogenCreateBlocks.CENTRIFUGE));
    }
}
