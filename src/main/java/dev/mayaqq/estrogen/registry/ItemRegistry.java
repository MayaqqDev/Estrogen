package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.registry.items.HorseUrineBottleItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;
import static net.minecraft.item.Items.GLASS_BOTTLE;

public class ItemRegistry {
    public static final ItemGroup ESTROGEN_GROUP = FabricItemGroupBuilder.build(id("estrogen_group"), () -> new ItemStack(ItemRegistry.ESTROGEN_PILL));

    public static final Item ESTROGEN_PILL = new Item(new Item.Settings().food(FoodCompontentRegistry.ESTROGEN_PILL).maxCount(16).rarity(Rarity.RARE).group(ESTROGEN_GROUP));
    public static final Item CRYSTAL_ESTROGEN_PILL = new Item(new Item.Settings().food(FoodCompontentRegistry.CRYTAL_ESTROGEN_PILL).maxCount(16).rarity(Rarity.EPIC).group(ESTROGEN_GROUP));
    public static final Item BALLS = new Item(new Item.Settings().group(ESTROGEN_GROUP));
    public static final Item TESTOSTERONE_CHUNK = new Item(new Item.Settings().group(ESTROGEN_GROUP));
    public static final Item TESTOSTERONE_POWDER = new Item(new Item.Settings().group(ESTROGEN_GROUP));
    public static final HorseUrineBottleItem HORSE_URINE_BOTTLE = new HorseUrineBottleItem(new Item.Settings().recipeRemainder(GLASS_BOTTLE).food(FoodCompontentRegistry.HORSE_URINE_BOTTLE).maxCount(16).group(ESTROGEN_GROUP));
    public static final Item USED_FILTER = new Item(new Item.Settings().group(ESTROGEN_GROUP));
    public static final Item ESTROGEN_CHIP_COOKIE = new Item(new Item.Settings().food(FoodCompontentRegistry.ESTROGEN_CHIP_COOKIE).maxCount(16).group(ESTROGEN_GROUP));

    public static void register() {
        Registry.register(Registry.ITEM, id("estrogen_pill"), ESTROGEN_PILL);
        Registry.register(Registry.ITEM, id("crystal_estrogen_pill"), CRYSTAL_ESTROGEN_PILL);
        Registry.register(Registry.ITEM, id("balls"), BALLS);
        Registry.register(Registry.ITEM, id("testosterone_chunk"), TESTOSTERONE_CHUNK);
        Registry.register(Registry.ITEM, id("testosterone_powder"), TESTOSTERONE_POWDER);
        Registry.register(Registry.ITEM, id("horse_urine_bottle"), HORSE_URINE_BOTTLE);
        Registry.register(Registry.ITEM, id("used_filter"), USED_FILTER);
        Registry.register(Registry.ITEM, id("estrogen_chip_cookie"), ESTROGEN_CHIP_COOKIE);
    }
}