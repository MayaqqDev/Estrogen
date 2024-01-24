package dev.mayaqq.estrogen.registry.common;

import dev.architectury.registry.CreativeTabRegistry;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EstrogenCreativeTab {

    public static final CreativeModeTab ESTROGEN_TAB = CreativeTabRegistry.create(
            Estrogen.id("estrogen"),
            () -> new ItemStack(EstrogenItems.ESTROGEN_PILL.get()) // Icon
    );

    public static void register() {}
}
