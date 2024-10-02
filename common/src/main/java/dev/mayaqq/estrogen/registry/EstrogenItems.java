package dev.mayaqq.estrogen.registry;

import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.teamresourceful.resourcefullib.common.color.Color;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.registry.trinkets.EstrogenPatchesRenderer;
import dev.mayaqq.estrogen.client.registry.trinkets.ThighHighRenderer;
import dev.mayaqq.estrogen.registry.items.*;
import dev.mayaqq.estrogen.registry.tooltip.ThighHighsToolTipModifier;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import uwu.serenity.critter.creative.TabPlacement;
import uwu.serenity.critter.stdlib.items.ItemEntry;
import uwu.serenity.critter.stdlib.items.ItemRegistrar;

import static dev.mayaqq.estrogen.registry.Transgenders.*;

public class EstrogenItems {
    public static final ItemRegistrar ITEMS = ItemRegistrar.create(Estrogen.REGISTRIES);

    public static final ItemEntry<Item>
        ESTROGEN_PILL = ITEMS.entry("estrogen_pill", Item::new)
            .properties(p -> p.stacksTo(16)
                .food(EstrogenFoodComponents.ESTROGEN_PILL)
                .rarity(Rarity.RARE))
            .transform(potatoProjectile(b -> b.damage(3)
                .reloadTicks(8)
                .velocity(1.5f)
                .knockback(0.3f)
                .renderTumbling()
                .onEntityHit(EstrogenPotatoProjectiles.potion(EstrogenEffects.ESTROGEN_EFFECT.get(), 1, 200, true))))
            .transform(standardTooltip())
            .register(),
        CRYSTAL_ESTROGEN_PILL = ITEMS.entry("crystal_estrogen_pill", Item::new)
            .properties(p -> p.stacksTo(16)
                .food(EstrogenFoodComponents.CRYTAL_ESTROGEN_PILL)
                .rarity(Rarity.EPIC))
            .transform(potatoProjectile(b -> b.damage(3)
                .reloadTicks(8)
                .velocity(1.5f)
                .knockback(0.3f)
                .renderTumbling()
                .onEntityHit(EstrogenPotatoProjectiles.potion(EstrogenEffects.ESTROGEN_EFFECT.get(), 2, 300, true))))
            .transform(standardTooltip())
            .register(),
        BALLS = ITEMS.entry("balls", Item::new)
            .transform(potatoProjectile(b -> b.damage(5)
                .reloadTicks(15)
                .knockback(0.05f)
                .velocity(1.25f)
                .renderTumbling()
                .onEntityHit(EstrogenPotatoProjectiles.potion(MobEffects.CONFUSION, 1, 160, true))))
            .register(),
        TESTOSTERONE_CHUNK = ITEMS.entry("testosterone_chunk", Item::new).register(),
        TESTOSTERONE_POWDER = ITEMS.entry("testosterone_powder", Item::new).register(),
        USED_FILTER = ITEMS.entry("used_filter", Item::new).register(),
        MOTH_FUZZ = ITEMS.entry("moth_fuzz", Item::new).creativeTab(CreativeModeTabs.INGREDIENTS, TabPlacement.before(Items.INK_SAC)).register();

    public static final ItemEntry<EstrogenCookieItem> ESTROGEN_CHIP_COOKIE = ITEMS.entry("estrogen_chip_cookie", EstrogenCookieItem::new)
        .properties(p -> p.food(EstrogenFoodComponents.ESTROGEN_CHIP_COOKIE)
            .stacksTo(64)
            .rarity(Rarity.RARE))
        .creativeTab(CreativeModeTabs.FOOD_AND_DRINKS, TabPlacement.after(Items.COOKIE))
        .register();
    public static final ItemEntry<HorseUrineBottleItem> HORSE_URINE_BOTTLE = ITEMS.entry("horse_urine_bottle", HorseUrineBottleItem::new)
        .properties(p -> p.food(EstrogenFoodComponents.HORSE_URINE_BOTTLE)
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16))
        .register();
    public static final ItemEntry<EstrogenPatchesItem> ESTROGEN_PATCHES = ITEMS.entry("estrogen_patches", EstrogenPatchesItem::new)
        .properties(p -> p.stacksTo(1))
        .transform(standardTooltip())
        .transform(baubleWithRenderer(() -> EstrogenPatchesRenderer::new))
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
        .transform(tooltip(ThighHighsToolTipModifier::create))
        .transform(baubleWithRenderer(() -> ThighHighRenderer::new))
        .onSetup(item -> CauldronInteraction.WATER.put(item, ThighHighsItem.CAULDRON_INTERACTION))
        .colors(() -> ThighHighsItem::getItemColor)
        .register();
    public static final ItemEntry<MothElytraItem> MOTH_ELYTRA = ITEMS.entry("moth_elytra", MothElytraItem::new)
        .properties(p -> p.stacksTo(1)
            .durability(626)
            .rarity(Rarity.UNCOMMON))
        .creativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES, TabPlacement.after(Items.ELYTRA))
        .register();
    public static final ItemEntry<GenderChangePotionItem> GENDER_CHANGE_POTION = ITEMS.entry("gender_change_potion", GenderChangePotionItem::new)
        .properties(p -> p.stacksTo(1).rarity(Rarity.RARE))
        .creativeTab(CreativeModeTabs.FOOD_AND_DRINKS, TabPlacement.after(Items.HONEY_BOTTLE))
        .register();


    @SuppressWarnings("unchecked")
    public static final ItemEntry<DreamBottleItem> DREAM_BOTTLE = (ItemEntry<DreamBottleItem>) EstrogenBlocks.DREAM_BLOCK.getItemEntry();

    public static void bucketProperties(Item.Properties props) {
        props.craftRemainder(Items.BUCKET).stacksTo(1);
    }
}
