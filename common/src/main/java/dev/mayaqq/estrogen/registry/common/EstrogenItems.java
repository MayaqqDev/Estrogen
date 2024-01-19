package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.items.EstrogenCookieItem;
import dev.mayaqq.estrogen.registry.common.items.EstrogenPatchesItem;
import dev.mayaqq.estrogen.registry.common.items.HorseUrineBottleItem;
import dev.mayaqq.estrogen.registry.common.items.UwUItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;

public class EstrogenItems {
    public static final ItemEntry<Item>
        ESTROGEN_PILL = normalItem("estrogen_pill", new EstrogenProperties().food(EstrogenFoodComponents.ESTROGEN_PILL).stacksTo(16).rarity(Rarity.RARE)),
        CRYSTAL_ESTROGEN_PILL = normalItem("crystal_estrogen_pill", new EstrogenProperties().food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL).stacksTo(16).rarity(Rarity.EPIC)),
        BALLS = normalItem("balls", new EstrogenProperties()),
        TESTOSTERONE_CHUNK = normalItem("testosterone_chunk", new EstrogenProperties()),
        TESTOSTERONE_POWDER = normalItem("testosterone_powder", new EstrogenProperties()),
        USED_FILTER = normalItem("used_filter", new EstrogenProperties());

    public static final ItemEntry<EstrogenCookieItem> ESTROGEN_CHIP_COOKIE = REGISTRATE.item("estrogen_chip_cookie", EstrogenCookieItem::new)
            .properties(p -> new EstrogenProperties().rarity(Rarity.RARE).food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE).stacksTo(1))
            .register();
    public static final ItemEntry<HorseUrineBottleItem> HORSE_URINE_BOTTLE = REGISTRATE.item("horse_urine_bottle", HorseUrineBottleItem::new)
            .properties(p -> new EstrogenProperties().craftRemainder(Items.GLASS_BOTTLE).food(EstrogenFoodComponents.HORSE_URINE_BOTTLE).stacksTo(16))
            .register();
    public static final ItemEntry<EstrogenPatchesItem> ESTROGEN_PATCHES = REGISTRATE.item("estrogen_patches", EstrogenPatchesItem::new).properties(p -> new EstrogenProperties().stacksTo(4)).register();
    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_ESTROGEN_PATCH = REGISTRATE.item("incomplete_estrogen_patches", SequencedAssemblyItem::new).properties(p -> p.stacksTo(1)).register();
    public static final ItemEntry<UwUItem> UWU = REGISTRATE.item("uwu", UwUItem::new).properties(p -> new EstrogenProperties().stacksTo(1)).register();
    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_UWU = REGISTRATE.item("incomplete_uwu", SequencedAssemblyItem::new).properties(p -> p.stacksTo(1)).register();

    private static ItemEntry<Item> normalItem(String name, Item.Properties properties) {
        return Estrogen.REGISTRATE.item(name, Item::new)
                .properties(p -> properties)
                .register();
    }

    public static void register() {}

    public static class EstrogenProperties extends Item.Properties {
        public EstrogenProperties() {
            super();
            this.arch$tab(EstrogenCreativeTab.ESTROGEN_TAB);
        }
    }
}