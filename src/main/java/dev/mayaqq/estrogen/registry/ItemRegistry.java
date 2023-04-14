package dev.mayaqq.estrogen.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class ItemRegistry {
    public static final ItemGroup ESTROGEN_GROUP = FabricItemGroupBuilder.build(id("estrogen_group"), () -> new ItemStack(ItemRegistry.ESTROGEN_PILL));

    public static final Item ESTROGEN_PILL = new Item(new Item.Settings().food(FoodCompontentRegistry.ESTROGEN_PILL).maxCount(16).rarity(Rarity.RARE).group(ESTROGEN_GROUP));
    public static final Item BALLS = new Item(new Item.Settings().group(ESTROGEN_GROUP));
    public static final Item TESTOSTERONE_CHUNK = new Item(new Item.Settings().group(ESTROGEN_GROUP));
    public static final Item TESTOSTERONE_POWDER = new Item(new Item.Settings().group(ESTROGEN_GROUP));

    public static void register() {
        Registry.register(Registry.ITEM, id("estrogen_pill"), ESTROGEN_PILL);
        Registry.register(Registry.ITEM, id("balls"), BALLS);
        Registry.register(Registry.ITEM, id("testosterone_chunk"), TESTOSTERONE_CHUNK);
        Registry.register(Registry.ITEM, id("testosterone_powder"), TESTOSTERONE_POWDER);
    }
}