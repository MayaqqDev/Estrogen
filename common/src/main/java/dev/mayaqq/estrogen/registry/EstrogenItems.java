package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class EstrogenItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Estrogen.MOD_ID);

    public static final RegistryEntry<Item> MOLTEN_SLIME_BUCKET = ITEMS.register("molten_slime_bucket", () -> new FluidBucketItem(
            EstrogenFluidProperties.MOLTEN_SLIME,
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
    ));

    public static final RegistryEntry<Item> TESTOSTERONE_MIXTURE_BUCKET = ITEMS.register("testosterone_mixture_bucket", () -> new FluidBucketItem(
            EstrogenFluidProperties.TESTOSTERONE_MIXTURE,
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
    ));

    public static final RegistryEntry<Item> LIQUID_ESTROGEN_BUCKET = ITEMS.register("liquid_estrogen_bucket", () -> new FluidBucketItem(
            EstrogenFluidProperties.LIQUID_ESTROGEN,
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
    ));

    public static final RegistryEntry<Item> FILTRATED_HORSE_URINE_BUCKET = ITEMS.register("filtrated_horse_urine_bucket", () -> new FluidBucketItem(
            EstrogenFluidProperties.FILTRATED_HORSE_URINE,
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
    ));

    public static final RegistryEntry<Item> HORSE_URINE_BUCKET = ITEMS.register("horse_urine_bucket", () -> new FluidBucketItem(
            EstrogenFluidProperties.HORSE_URINE,
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
    ));

    public static final RegistryEntry<Item> MOLTEN_AMETHYST_BUCKET = ITEMS.register("molten_amethyst_bucket", () -> new FluidBucketItem(
            EstrogenFluidProperties.MOLTEN_AMETHYST,
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
    ));
}
