package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import dev.mayaqq.estrogen.registry.common.items.EstrogenPatchesItem;
import dev.mayaqq.estrogen.registry.common.items.HorseUrineBottleItem;
import dev.mayaqq.estrogen.registry.common.items.UwUItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;
import static net.minecraft.item.Items.GLASS_BOTTLE;

public class EstrogenItems {
    public static final ItemGroup ESTROGEN_GROUP = FabricItemGroupBuilder.build(id("estrogen_group"), () -> new ItemStack(EstrogenItems.ESTROGEN_PILL));

    public static final Item ESTROGEN_PILL = register("estrogen_pill", new Item(new Item.Settings().food(EstrogenFoodComponents.ESTROGEN_PILL).maxCount(16).rarity(Rarity.RARE).group(ESTROGEN_GROUP)));
    public static final Item CRYSTAL_ESTROGEN_PILL = register("crystal_estrogen_pill", new Item(new Item.Settings().food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL).maxCount(16).rarity(Rarity.EPIC).group(ESTROGEN_GROUP)));
    public static final Item BALLS = register("balls", new Item(new Item.Settings().group(ESTROGEN_GROUP)));
    public static final Item TESTOSTERONE_CHUNK = register("testosterone_chunk", new Item(new Item.Settings().group(ESTROGEN_GROUP)));
    public static final Item TESTOSTERONE_POWDER = register("testosterone_powder", new Item(new Item.Settings().group(ESTROGEN_GROUP)));
    public static final Item HORSE_URINE_BOTTLE = register("horse_urine_bottle", new HorseUrineBottleItem(new Item.Settings().recipeRemainder(GLASS_BOTTLE).food(EstrogenFoodComponents.HORSE_URINE_BOTTLE).maxCount(16).group(ESTROGEN_GROUP)));
    public static final Item USED_FILTER = register("used_filter", new Item(new Item.Settings().group(ESTROGEN_GROUP)));
    public static final Item ESTROGEN_CHIP_COOKIE = register("estrogen_chip_cookie", new Item(new Item.Settings().food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE).maxCount(16).group(ESTROGEN_GROUP)));
    public static final Item ESTROGEN_PATCHES = register("estrogen_patches", new EstrogenPatchesItem(new Item.Settings().maxCount(4).group(ESTROGEN_GROUP)));
    public static final Item INCOMPLETE_ESTROGEN_PATCH = register("incomplete_estrogen_patches", new SequencedAssemblyItem(new Item.Settings().maxCount(1).group(ESTROGEN_GROUP)));
    public static final Item UWU = register("uwu", new UwUItem(new Item.Settings().maxCount(1).group(ESTROGEN_GROUP)));
    public static final Item INCOMPLETE_UWU = register("incomplete_uwu", new SequencedAssemblyItem(new Item.Settings().maxCount(1).group(ESTROGEN_GROUP)));

    public static void register() {}

    public static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, id(id), item);
    }
}