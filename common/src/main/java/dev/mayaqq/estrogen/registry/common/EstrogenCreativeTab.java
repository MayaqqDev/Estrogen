package dev.mayaqq.estrogen.registry.common;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EstrogenCreativeTab {
    public static final Registrar<CreativeModeTab> CREATIVE_TABS = Estrogen.MANAGER.get().get(Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> ESTROGEN_TAB = CREATIVE_TABS.register(
            Estrogen.id("estrogen"),
            () -> CreativeTabRegistry.create(
                    Component.translatable("itemGroup.estrogen"), // Tab Name
                    () -> new ItemStack(EstrogenItems.ESTROGEN_PILL) // Icon
            )
    );

    public static void register() {
    }
}
