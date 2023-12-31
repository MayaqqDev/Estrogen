package dev.mayaqq.estrogen.registry.common;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EstrogenCreativeTab {
    public static final DeferredRegister<CreativeModeTab> ESTROGEN_TAB =
            DeferredRegister.create(Estrogen.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> ESTROGEN_TAB_SUPPLIER = ESTROGEN_TAB.register(
            "estrogen",
            () -> CreativeTabRegistry.create(
                    Component.translatable("itemGroup.estrogen"), // Tab Name
                    () -> new ItemStack(EstrogenItems.ESTROGEN_PILL) // Icon
            )
    );
}
