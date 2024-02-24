package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.items.EstrogenCookieItem;
import dev.mayaqq.estrogen.registry.items.EstrogenPatchesItem;
import dev.mayaqq.estrogen.registry.items.HorseUrineBottleItem;
import dev.mayaqq.estrogen.registry.items.UwUItem;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class EstrogenItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Estrogen.MOD_ID);

    public static final ResourcefulRegistry<Item> BASIC_ITEMS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> BUCKETS = ResourcefulRegistries.create(ITEMS);

    // Basic items
    public static final RegistryEntry<Item>
        ESTROGEN_PILL = BASIC_ITEMS.register("estrogen_pill", () -> new Item(new Item.Properties().food(EstrogenFoodComponents.ESTROGEN_PILL).stacksTo(16).rarity(Rarity.RARE))),
        CRYSTAL_ESTROGEN_PILL = BASIC_ITEMS.register("crystal_estrogen_pill", () -> new Item(new Item.Properties().food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL).stacksTo(16).rarity(Rarity.EPIC))),
        BALLS = BASIC_ITEMS.register("balls", () -> new Item(new Item.Properties())),
        TESTOSTERONE_CHUNK = BASIC_ITEMS.register("testosterone_chunk", () -> new Item(new Item.Properties())),
        TESTOSTERONE_POWDER = BASIC_ITEMS.register("testosterone_powder", () -> new Item(new Item.Properties())),
        USED_FILTER = BASIC_ITEMS.register("used_filter", () -> new Item(new Item.Properties()));

    // Special items
    public static final RegistryEntry<Item> ESTROGEN_CHIP_COOKIE = BASIC_ITEMS.register("estrogen_chip_cookie", () ->
            new EstrogenCookieItem(new Item.Properties().food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE).stacksTo(64).rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> HORSE_URINE_BOTTLE = BASIC_ITEMS.register("horse_urine_bottle", () ->
            new HorseUrineBottleItem(new Item.Properties().food(EstrogenFoodComponents.HORSE_URINE_BOTTLE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryEntry<EstrogenPatchesItem> ESTROGEN_PATCHES = BASIC_ITEMS.register("estrogen_patches", () ->
            new EstrogenPatchesItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> INCOMPLETE_ESTROGEN_PATCH = BASIC_ITEMS.register("incomplete_estrogen_patches", () ->
            new SequencedAssemblyItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> UWU = BASIC_ITEMS.register("uwu", () ->
            new UwUItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> INCOMPLETE_UWU = BASIC_ITEMS.register("incomplete_uwu", () ->
            new SequencedAssemblyItem(new Item.Properties().stacksTo(1)));

    // Buckets
    public static final RegistryEntry<Item> MOLTEN_SLIME_BUCKET = BUCKETS.register("molten_slime_bucket", () -> new FluidBucketItem(EstrogenFluidProperties.MOLTEN_SLIME, bucketProperties()));
    public static final RegistryEntry<Item> TESTOSTERONE_MIXTURE_BUCKET = BUCKETS.register("testosterone_mixture_bucket", () -> new FluidBucketItem(EstrogenFluidProperties.TESTOSTERONE_MIXTURE, bucketProperties()));
    public static final RegistryEntry<Item> LIQUID_ESTROGEN_BUCKET = BUCKETS.register("liquid_estrogen_bucket", () -> new FluidBucketItem(EstrogenFluidProperties.LIQUID_ESTROGEN, bucketProperties()));
    public static final RegistryEntry<Item> FILTRATED_HORSE_URINE_BUCKET = BUCKETS.register("filtrated_horse_urine_bucket", () -> new FluidBucketItem(EstrogenFluidProperties.FILTRATED_HORSE_URINE, bucketProperties()));
    public static final RegistryEntry<Item> HORSE_URINE_BUCKET = BUCKETS.register("horse_urine_bucket", () -> new FluidBucketItem(EstrogenFluidProperties.HORSE_URINE, bucketProperties()));
    public static final RegistryEntry<Item> MOLTEN_AMETHYST_BUCKET = BUCKETS.register("molten_amethyst_bucket", () -> new FluidBucketItem(EstrogenFluidProperties.MOLTEN_AMETHYST, bucketProperties()));
    public static Item.Properties bucketProperties() {
        return new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1);
    }
}
