package dev.mayaqq.estrogen.registry;

import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class EstrogenFluidItems {
    public static Registrar<Item> FLUID_ITEMS = Estrogen.MANAGER.get().get(Registries.ITEM);

    public static final RegistrySupplier<Item> MOLTEN_SLIME_BUCKET = FLUID_ITEMS.register(Estrogen.id("molten_slime_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.MOLTEN_SLIME, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB).stacksTo(1)));
    public static final RegistrySupplier<Item> TESTOSTERONE_MIXTURE_BUCKET = FLUID_ITEMS.register(Estrogen.id("testosterone_mixture_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.TESTOSTERONE_MIXTURE, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB).stacksTo(1)));
    public static final RegistrySupplier<Item> LIQUID_ESTROGEN_BUCKET = FLUID_ITEMS.register(Estrogen.id("liquid_estrogen_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.LIQUID_ESTROGEN, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB).stacksTo(1)));
    public static final RegistrySupplier<Item> FILTRATED_HORSE_URINE_BUCKET = FLUID_ITEMS.register(Estrogen.id("filtrated_horse_urine_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.FILTRATED_HORSE_URINE, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB).stacksTo(1)));
    public static final RegistrySupplier<Item> HORSE_URINE_BUCKET = FLUID_ITEMS.register(Estrogen.id("horse_urine_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.HORSE_URINE, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB).stacksTo(1)));
    public static final RegistrySupplier<Item> MOLTEN_AMETHYST_BUCKET = FLUID_ITEMS.register(Estrogen.id("molten_amethyst_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.MOLTEN_AMETHYST, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB).stacksTo(1)));

    public static void register() {
    }
}
