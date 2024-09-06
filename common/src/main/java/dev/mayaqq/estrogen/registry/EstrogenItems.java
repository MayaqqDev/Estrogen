package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.teamresourceful.resourcefullib.common.color.Color;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.items.*;
import dev.mayaqq.estrogen.registry.tooltip.ThighHighsToolTipModifier;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import uwu.serenity.critter.stdlib.items.ItemEntry;
import uwu.serenity.critter.stdlib.items.ItemRegistrar;

public class EstrogenItems {
    public static final ItemRegistrar ITEMS = ItemRegistrar.create(Estrogen.MOD_ID);

    public static final ResourcefulRegistry<Item> BUCKETS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Estrogen.MOD_ID);

    public static final ItemEntry<Item>
        ESTROGEN_PILL = ITEMS.entry("estrogen_pill", Item::new).properties(p -> p.stacksTo(16).food(EstrogenFoodComponents.ESTROGEN_PILL).rarity(Rarity.RARE)).transform(Transgenders.standardTooltip()).register(),
        CRYSTAL_ESTROGEN_PILL = ITEMS.entry("crystal_estrogen_pill", Item::new).properties(p -> p.stacksTo(16).food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL).rarity(Rarity.EPIC)).transform(Transgenders.standardTooltip()).register(),
        BALLS = ITEMS.entry("balls", Item::new).register(),
        TESTOSTERONE_CHUNK = ITEMS.entry("testosterone_chunk", Item::new).register(),
        TESTOSTERONE_POWDER = ITEMS.entry("testosterone_powder", Item::new).register(),
        USED_FILTER = ITEMS.entry("used_filter", Item::new).register(),
        MOTH_FUZZ = ITEMS.entry("moth_fuzz", Item::new).register();

    public static final ItemEntry<EstrogenCookieItem> ESTROGEN_CHIP_COOKIE = ITEMS.entry("estrogen_chip_cookie", EstrogenCookieItem::new)
        .properties(p -> p.food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE)
            .stacksTo(64)
            .rarity(Rarity.RARE))
        .register();
    public static final ItemEntry<HorseUrineBottleItem> HORSE_URINE_BOTTLE = ITEMS.entry("horse_urine_bottle", HorseUrineBottleItem::new)
        .properties(p -> p.food(EstrogenFoodComponents.HORSE_URINE_BOTTLE)
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16))
        .register();
    public static final ItemEntry<EstrogenPatchesItem> ESTROGEN_PATCHES = ITEMS.entry("estrogen_patches", EstrogenPatchesItem::new)
        .properties(p -> p.stacksTo(1))
        .transform(Transgenders.standardTooltip())
        .register();
    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_ESTROGEN_PATCH = ITEMS.entry("incomplete_estrogen_patches", SequencedAssemblyItem::new)
        .properties(p -> p.stacksTo(1))
        .register();
    public static final ItemEntry<UwUItem> UWU = ITEMS.entry("uwu", UwUItem::new)
        .properties(p -> p.stacksTo(1))
        .register();
    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_UWU = ITEMS.entry("incomplete_uwu", SequencedAssemblyItem::new)
        .properties(p -> p.stacksTo(1))
        .register();
    public static final ItemEntry<ThighHighsItem> THIGH_HIGHS = ITEMS.entry("thigh_highs", p -> new ThighHighsItem(p, Color.parseColor("#f1d85a"),  Color.parseColor("0xff4ea5")))
        .properties(p -> p.stacksTo(1))
        .transform(Transgenders.tooltip(ThighHighsToolTipModifier::create))
        .colors(() -> ThighHighsItem::getItemColor)
        .register();
    public static final ItemEntry<MothElytraItem> MOTH_ELYTRA = ITEMS.entry("moth_elytra", MothElytraItem::new)
        .properties(p -> p.stacksTo(1)
            .durability(626)
            .rarity(Rarity.UNCOMMON))
        .register();


    public static final ItemEntry<DreamBottleItem> DREAM_BOTTLE = (ItemEntry<DreamBottleItem>) EstrogenBlocks.DREAM_BLOCK.getItemEntry();

    // Eggs
    //public static final RegistryEntry<Item> MOTH_EGG = SPAWN_EGGS.register("moth_spawn_egg", CommonPlatform.createSpawnEggItem(EstrogenEntities.MOTH, Color.parseColor("#ffc514"), Color.parseColor("#ff83c0"), new Item.Properties()));

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
