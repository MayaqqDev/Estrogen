package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.items.EstrogenPatchesItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static dev.mayaqq.estrogen.registry.EstrogenCreateItems.*;

public class EstrogenCreativeTab {
    public static final Supplier<CreativeModeTab> ESTROGEN_TAB = new ResourcefulCreativeTab(Estrogen.id("estrogen"))
            .setItemIcon(EstrogenCreateItems.ESTROGEN_PILL)
            .addContent(EstrogenCreativeTab::creativeTabItems)
            .build();

    public static Stream<ItemStack> creativeTabItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(ESTROGEN_PILL.get().getDefaultInstance());
        items.add(CRYSTAL_ESTROGEN_PILL.get().getDefaultInstance());
        items.add(BALLS.get().getDefaultInstance());
        items.add(TESTOSTERONE_CHUNK.get().getDefaultInstance());
        items.add(TESTOSTERONE_POWDER.get().getDefaultInstance());
        items.add(USED_FILTER.get().getDefaultInstance());
        items.add(ESTROGEN_CHIP_COOKIE.get().getDefaultInstance());
        items.add(HORSE_URINE_BOTTLE.get().getDefaultInstance());
        items.add(ESTROGEN_PATCHES.get().getDefaultInstance());
        items.add(EstrogenPatchesItem.emptyStack());
        items.add(UWU.get().getDefaultInstance());
        items.add(EstrogenCreateBlocks.CENTRIFUGE.get().asItem().getDefaultInstance());
        items.add(EstrogenItems.MOLTEN_SLIME_BUCKET.get().getDefaultInstance());
        items.add(EstrogenItems.TESTOSTERONE_MIXTURE_BUCKET.get().getDefaultInstance());
        items.add(EstrogenItems.LIQUID_ESTROGEN_BUCKET.get().getDefaultInstance());
        items.add(EstrogenItems.FILTRATED_HORSE_URINE_BUCKET.get().getDefaultInstance());
        items.add(EstrogenItems.HORSE_URINE_BUCKET.get().getDefaultInstance());
        items.add(EstrogenItems.MOLTEN_AMETHYST_BUCKET.get().getDefaultInstance());
        //TODO: Add buckets
        return items.stream();
    }

    public static void init() {}
}
