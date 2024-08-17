package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.teamresourceful.resourcefullib.common.color.Color;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.platform.CommonPlatform;
import dev.mayaqq.estrogen.registry.items.*;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;

public class EstrogenItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Estrogen.MOD_ID);

    public static final ResourcefulRegistry<Item> BASIC_ITEMS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> BUCKETS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> BLOCK_ITEMS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> SPAWN_EGGS = ResourcefulRegistries.create(ITEMS);

    // Basic items
    public static final RegistryEntry<Item>
        ESTROGEN_PILL = BASIC_ITEMS.register("estrogen_pill", () -> new Item(new Item.Properties().food(EstrogenFoodComponents.ESTROGEN_PILL).stacksTo(16).rarity(Rarity.RARE))),
        CRYSTAL_ESTROGEN_PILL = BASIC_ITEMS.register("crystal_estrogen_pill", () -> new Item(new Item.Properties().food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL).stacksTo(16).rarity(Rarity.EPIC))),
        BALLS = BASIC_ITEMS.register("balls", () -> new Item(new Item.Properties())),
        TESTOSTERONE_CHUNK = BASIC_ITEMS.register("testosterone_chunk", () -> new Item(new Item.Properties())),
        TESTOSTERONE_POWDER = BASIC_ITEMS.register("testosterone_powder", () -> new Item(new Item.Properties())),
        USED_FILTER = BASIC_ITEMS.register("used_filter", () -> new Item(new Item.Properties())),
        MOTH_FUZZ = BASIC_ITEMS.register("moth_fuzz", () -> new Item(new Item.Properties()));

    // Special items
    public static final RegistryEntry<Item> ESTROGEN_CHIP_COOKIE = BASIC_ITEMS.register("estrogen_chip_cookie", () ->
            new EstrogenCookieItem(new Item.Properties().food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE).stacksTo(64).rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> HORSE_URINE_BOTTLE = BASIC_ITEMS.register("horse_urine_bottle", () ->
            new HorseUrineBottleItem(new Item.Properties().food(EstrogenFoodComponents.HORSE_URINE_BOTTLE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final RegistryEntry<EstrogenPatchesItem> ESTROGEN_PATCHES = BASIC_ITEMS.register("estrogen_patches", () -> new EstrogenPatchesItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> INCOMPLETE_ESTROGEN_PATCH = BASIC_ITEMS.register("incomplete_estrogen_patches", () -> new SequencedAssemblyItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> UWU = BASIC_ITEMS.register("uwu", () -> new UwUItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> INCOMPLETE_UWU = BASIC_ITEMS.register("incomplete_uwu", () -> new SequencedAssemblyItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<ThighHighsItem> THIGH_HIGHS = BASIC_ITEMS.register("thigh_highs", () -> new ThighHighsItem(new Item.Properties().stacksTo(1), Color.parseColor("#ffa600"), Color.parseColor("#ff4ea5")));
    public static final RegistryEntry<MothElytraItem> MOTH_ELYTRA = BASIC_ITEMS.register("moth_elytra", () -> new MothElytraItem(new Item.Properties().stacksTo(1).durability(626).rarity(Rarity.UNCOMMON)));

    // Eggs
    public static final RegistryEntry<Item> MOTH_EGG = SPAWN_EGGS.register("moth_spawn_egg", CommonPlatform.createSpawnEggItem(EstrogenEntities.MOTH, Color.parseColor("#ffc514"), Color.parseColor("#ff83c0"), new Item.Properties()));

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

    // Block Items
    public static final RegistryEntry<Item> CENTRIFUGE = BLOCK_ITEMS.register("centrifuge", () -> new BlockItem(EstrogenBlocks.CENTRIFUGE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> COOKIE_JAR = BLOCK_ITEMS.register("cookie_jar", () -> new BlockItem(EstrogenBlocks.COOKIE_JAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DREAM_BOTTLE = BLOCK_ITEMS.register("dream_bottle", () -> new DreamBottleItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryEntry<Item> DORMANT_DREAM_BLOCK = BLOCK_ITEMS.register("dormant_dream_block", () -> new BlockItem(EstrogenBlocks.DORMANT_DREAM_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ESTROGEN_PILL_BLOCK = BLOCK_ITEMS.register("estrogen_pill_block", () -> new BlockItem(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOTH_WOOL = BLOCK_ITEMS.register("moth_wool", () -> new BlockItem(EstrogenBlocks.MOTH_WOOL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOTH_SEAT = BLOCK_ITEMS.register("moth_seat", () -> new BlockItem(EstrogenBlocks.MOTH_SEAT.get(), new Item.Properties()));

    public static void registerTooltips() {
        EstrogenItems.ITEMS.stream().forEach(itemEntry -> TooltipModifier.REGISTRY.registerDeferred(itemEntry.getId(), item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)));
    }
}
