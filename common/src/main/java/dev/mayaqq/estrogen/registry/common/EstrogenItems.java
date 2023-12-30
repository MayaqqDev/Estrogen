package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import dev.mayaqq.estrogen.registry.common.items.EstrogenPatchesItem;
import dev.mayaqq.estrogen.registry.common.items.HorseUrineBottleItem;
import dev.mayaqq.estrogen.registry.common.items.UwUItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import static dev.mayaqq.estrogen.Estrogen.id;
import static net.minecraft.item.Items.GLASS_BOTTLE;

public class EstrogenItems {

    public static final Item ESTROGEN_PILL = register("estrogen_pill", new Item(new Item.Settings().food(EstrogenFoodComponents.ESTROGEN_PILL).maxCount(16).rarity(Rarity.RARE)));
    public static final Item CRYSTAL_ESTROGEN_PILL = register("crystal_estrogen_pill", new Item(new Item.Settings().food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL).maxCount(16).rarity(Rarity.EPIC)));
    public static final Item BALLS = register("balls", new Item(new Item.Settings()));
    public static final Item TESTOSTERONE_CHUNK = register("testosterone_chunk", new Item(new Item.Settings()));
    public static final Item TESTOSTERONE_POWDER = register("testosterone_powder", new Item(new Item.Settings()));
    public static final Item HORSE_URINE_BOTTLE = register("horse_urine_bottle", new HorseUrineBottleItem(new Item.Settings().recipeRemainder(GLASS_BOTTLE).food(EstrogenFoodComponents.HORSE_URINE_BOTTLE).maxCount(16)));
    public static final Item USED_FILTER = register("used_filter", new Item(new Item.Settings()));
    public static final Item ESTROGEN_CHIP_COOKIE = register("estrogen_chip_cookie", new Item(new Item.Settings().food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE).maxCount(16)));
    public static final Item ESTROGEN_PATCHES = register("estrogen_patches", new EstrogenPatchesItem(new Item.Settings().maxCount(4)));
    public static final Item INCOMPLETE_ESTROGEN_PATCH = register("incomplete_estrogen_patches", new SequencedAssemblyItem(new Item.Settings().maxCount(1)));
    public static final Item UWU = register("uwu", new UwUItem(new Item.Settings().maxCount(1)));
    public static final Item INCOMPLETE_UWU = register("incomplete_uwu", new SequencedAssemblyItem(new Item.Settings().maxCount(1)));

    public static final ItemGroup ESTROGEN_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ESTROGEN_PILL))
            .name(Text.translatable("itemGroup.estrogen"))
            .build();
    public static final RegistryKey<ItemGroup> ESTROGEN_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, id("estrogen"));

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, id("estrogen"), ESTROGEN_GROUP);
        ItemGroupEvents.modifyEntriesEvent(ESTROGEN_GROUP_KEY).register(content -> {
            boolean checked = false;
            for (Item item : Registries.ITEM) {
                if (item.getTranslationKey().split("\\.")[1].equals("estrogen")) {
                    content.addStack(item.getDefaultStack());
                    checked = true;
                } else if (checked) {
                    break;
                }
            }
        });
    }

    public static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, id(id), item);
    }
}