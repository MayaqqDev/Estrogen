package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import dev.mayaqq.estrogen.registry.common.items.EstrogenPatchesItem;
import dev.mayaqq.estrogen.registry.common.items.HorseUrineBottleItem;
import dev.mayaqq.estrogen.registry.common.items.UwUItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenItems {

    public static final Item ESTROGEN_PILL = register("estrogen_pill", new Item(new Item.Properties().food(EstrogenFoodComponents.ESTROGEN_PILL).stacksTo(16).rarity(Rarity.RARE)));
    public static final Item CRYSTAL_ESTROGEN_PILL = register("crystal_estrogen_pill", new Item(new Item.Properties().food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL).stacksTo(16).rarity(Rarity.EPIC)));
    public static final Item BALLS = register("balls", new Item(new Item.Properties()));
    public static final Item TESTOSTERONE_CHUNK = register("testosterone_chunk", new Item(new Item.Properties()));
    public static final Item TESTOSTERONE_POWDER = register("testosterone_powder", new Item(new Item.Properties()));
    public static final Item HORSE_URINE_BOTTLE = register("horse_urine_bottle", new HorseUrineBottleItem(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(EstrogenFoodComponents.HORSE_URINE_BOTTLE).stacksTo(16)));
    public static final Item USED_FILTER = register("used_filter", new Item(new Item.Properties()));
    public static final Item ESTROGEN_CHIP_COOKIE = register("estrogen_chip_cookie", new Item(new Item.Properties().food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE).stacksTo(16)));
    public static final Item ESTROGEN_PATCHES = register("estrogen_patches", new EstrogenPatchesItem(new Item.Properties().stacksTo(4)));
    public static final Item INCOMPLETE_ESTROGEN_PATCH = register("incomplete_estrogen_patches", new SequencedAssemblyItem(new Item.Properties().stacksTo(1)));
    public static final Item UWU = register("uwu", new UwUItem(new Item.Properties().stacksTo(1)));
    public static final Item INCOMPLETE_UWU = register("incomplete_uwu", new SequencedAssemblyItem(new Item.Properties().stacksTo(1)));


    public static final CreativeModeTab ESTROGEN_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ESTROGEN_PILL))
            .name(Component.translatable("itemGroup.estrogen"))
            .build();
    public static final ResourceKey<CreativeModeTab> ESTROGEN_GROUP_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id("estrogen"));

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id("estrogen"), ESTROGEN_GROUP);
        ItemGroupEvents.modifyEntriesEvent(ESTROGEN_GROUP_KEY).register(content -> {
            boolean checked = false;
            for (Item item : BuiltInRegistries.ITEM) {
                if (item.getDescriptionId().split("\\.")[1].equals("estrogen")) {
                    content.addStack(item.getDefaultInstance());
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