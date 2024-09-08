package dev.mayaqq.estrogen.datagen.impl.tags;

import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.datagen.base.platform.PlatformHelper;
import dev.mayaqq.estrogen.datagen.base.tags.BaseTagProvider;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends BaseTagProvider.ItemProvider {

    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, PlatformHelper helper) {
        super(output, completableFuture, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(EstrogenTags.Items.THIGHS)
                .add(EstrogenItems.ESTROGEN_PATCHES.get())
                .add(EstrogenItems.THIGH_HIGHS.get());
        getOrCreateTagBuilder(EstrogenTags.Items.UWUFYING)
                .add(EstrogenItems.UWU.get());
        getOrCreateTagBuilder(EstrogenTags.Items.CURIOS_THIGHS)
                .add(EstrogenItems.ESTROGEN_PATCHES.get())
                .add(EstrogenItems.THIGH_HIGHS.get());
        getOrCreateTagBuilder(EstrogenTags.Items.MUSIC_DISCS)
                .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get());
        getOrCreateTagBuilder(EstrogenTags.Items.LAVA_BUCKETS)
                .add(EstrogenFluids.MOLTEN_SLIME.getBucket())
                .add(EstrogenFluids.MOLTEN_AMETHYST.getBucket());
        getOrCreateTagBuilder(EstrogenTags.Items.COOKIES)
                .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get())
                .add(Items.COOKIE);
        getOrCreateTagBuilder(EstrogenTags.Items.LEATHER_ITEMS)
                .add(Items.LEATHER)
                .add(Items.LEATHER_BOOTS)
                .add(Items.LEATHER_CHESTPLATE)
                .add(Items.LEATHER_HELMET)
                .add(Items.LEATHER_LEGGINGS)
                .add(Items.LEATHER_HORSE_ARMOR);
        getOrCreateTagBuilder(EstrogenTags.Items.LIGHT_EMITTERS)
                .add(Items.TORCH)
                .add(Items.TORCHFLOWER)
                .add(Items.LANTERN)
                .add(Items.SOUL_LANTERN)
                .add(Items.CANDLE);
        getOrCreateTagBuilder(AllTags.AllItemTags.SEATS.tag)
                .add(EstrogenBlocks.MOTH_SEAT.asItem());
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.WOOL)
                .add(EstrogenBlocks.MOTH_WOOL.asItem())
                .add(EstrogenBlocks.QUILTED_MOTH_WOOL.asItem());
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.WOOL_CARPETS)
                .add(EstrogenBlocks.MOTH_WOOL_CARPET.asItem())
                .add(EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET.asItem());
        getOrCreateTagBuilder(net.minecraft.tags.ItemTags.BEDS)
                .add(EstrogenBlocks.MOTH_BED.asItem())
                .add(EstrogenBlocks.QUILTED_MOTH_BED.asItem());
    }
}
