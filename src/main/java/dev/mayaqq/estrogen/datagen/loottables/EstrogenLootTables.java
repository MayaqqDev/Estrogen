package dev.mayaqq.estrogen.datagen.loottables;

import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class EstrogenLootTables extends FabricBlockLootTableProvider {
    public EstrogenLootTables(FabricDataGenerator fdg) {
        super(fdg);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(EstrogenBlocks.CENTRIFUGE.get(), EstrogenBlocks.CENTRIFUGE.get());
    }
}
