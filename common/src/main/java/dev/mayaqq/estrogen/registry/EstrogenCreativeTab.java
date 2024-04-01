package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static dev.mayaqq.estrogen.registry.EstrogenItems.*;

public class EstrogenCreativeTab {

    public static final EstrogenCreativeTab TABS = new EstrogenCreativeTab();

    public static final Supplier<CreativeModeTab> ESTROGEN_TAB = new ResourcefulCreativeTab(Estrogen.id("estrogen"))
            .setItemIcon(EstrogenItems.ESTROGEN_PILL)
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
        items.add(ESTROGEN_PATCHES.get().getFullStack());
        items.add(ESTROGEN_PATCHES.get().getDefaultInstance());
        items.add(UWU.get().getDefaultInstance());
        items.add(CENTRIFUGE.get().getDefaultInstance());
        items.add(COOKIE_JAR.get().getDefaultInstance());
        items.add(DREAM_BOTTLE.get().getDefaultInstance());
        items.add(DORMANT_DREAM_BLOCK.get().getDefaultInstance());
        items.add(ESTROGEN_PILL_BLOCK.get().getDefaultInstance());
        items.add(MOLTEN_SLIME_BUCKET.get().getDefaultInstance());
        items.add(TESTOSTERONE_MIXTURE_BUCKET.get().getDefaultInstance());
        items.add(LIQUID_ESTROGEN_BUCKET.get().getDefaultInstance());
        items.add(FILTRATED_HORSE_URINE_BUCKET.get().getDefaultInstance());
        items.add(HORSE_URINE_BUCKET.get().getDefaultInstance());
        items.add(MOLTEN_AMETHYST_BUCKET.get().getDefaultInstance());
        return items.stream();
    }

    public void init() {}
}
