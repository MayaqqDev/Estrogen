package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.registry.CreativeTabRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.items.EstrogenPatchesItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static dev.mayaqq.estrogen.registry.EstrogenItems.*;

public class EstrogenCreativeTab {
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
        items.add(ESTROGEN_PATCHES.get().getDefaultInstance());
        items.add(EstrogenPatchesItem.emptyStack());
        items.add(UWU.get().getDefaultInstance());
        items.add(EstrogenBlocks.CENTRIFUGE.get().asItem().getDefaultInstance());
        //TODO: Add buckets
        return items.stream();
    }

    public static void init() {}
}
