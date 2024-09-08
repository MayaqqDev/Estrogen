package dev.mayaqq.estrogen.datagen.impl.tags;

import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.datagen.base.platform.PlatformHelper;
import dev.mayaqq.estrogen.datagen.base.tags.BaseTagProvider;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BaseTagProvider.BlockProvider {

    public ModBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, PlatformHelper helper) {
        super(output, completableFuture, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(EstrogenTags.Blocks.PICKAXE_MINABLE)
                .add(EstrogenBlocks.CENTRIFUGE.get())
                .add(EstrogenBlocks.COOKIE_JAR.get())
                .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get())
                .add(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get());
        getOrCreateTagBuilder(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE)
                .add(EstrogenBlocks.MOTH_SEAT.get())
                .add(EstrogenBlocks.MOTH_BED.get())
                .add(EstrogenBlocks.QUILTED_MOTH_BED.get());

        getOrCreateTagBuilder(AllTags.AllBlockTags.SEATS.tag)
                .add(EstrogenBlocks.MOTH_SEAT.get());

        getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET)
                .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
        getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12)
                .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
        getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_OLD)
                .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
        getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12_OLD)
                .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
        getOrCreateTagBuilder(net.minecraft.tags.BlockTags.WOOL)
                .add(EstrogenBlocks.MOTH_WOOL.get())
                .add(EstrogenBlocks.QUILTED_MOTH_WOOL.get());
        getOrCreateTagBuilder(net.minecraft.tags.BlockTags.WOOL_CARPETS)
                .add(EstrogenBlocks.MOTH_WOOL_CARPET.get())
                .add(EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET.get());
        getOrCreateTagBuilder(net.minecraft.tags.BlockTags.BEDS)
                .add(EstrogenBlocks.MOTH_BED.get())
                .add(EstrogenBlocks.QUILTED_MOTH_BED.get());
    }
}
