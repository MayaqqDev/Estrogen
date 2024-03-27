package dev.mayaqq.estrogen.datagen.advancements;

import com.google.common.collect.ImmutableSet;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;

import java.util.Collection;
import java.util.function.Consumer;

public class EstrogenAdvancements extends FabricAdvancementProvider {
    public EstrogenAdvancements(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement().display(EstrogenItems.ESTROGEN_PILL.get(),
                Component.translatable("advancement.estrogen.root.title"),
                Component.translatable("advancement.estrogen.root.description"),
                Estrogen.id("textures/advancements/dream_block_background.png"),
                FrameType.TASK,
                true,
                true,
                false
        ).addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(getItems()))
                .build(Estrogen.id("root"));

        Advancement horseUrine = Advancement.Builder.advancement().parent(root).display(EstrogenItems.HORSE_URINE_BOTTLE.get(),
                Component.translatable("advancement.estrogen.horse_urine.title"),
                Component.translatable("advancement.estrogen.horse_urine.description"),
                null,
                FrameType.TASK,
                true,
                true,
                false
        ).addCriterion("horse_urine", InventoryChangeTrigger.TriggerInstance.hasItems(EstrogenItems.HORSE_URINE_BOTTLE.get()))
                .build(Estrogen.id("horse_urine"));

        Advancement usedFilter = Advancement.Builder.advancement().parent(horseUrine).display(EstrogenItems.USED_FILTER.get(),
                Component.translatable("advancement.estrogen.used_filter.title"),
                Component.translatable("advancement.estrogen.used_filter.description"),
                null,
                FrameType.TASK,
                true,
                true,
                false
        ).addCriterion("used_filter", InventoryChangeTrigger.TriggerInstance.hasItems(EstrogenItems.USED_FILTER.get()))
                .build(Estrogen.id("used_filter"));

        Advancement liquidEstrogen = Advancement.Builder.advancement().parent(usedFilter).display(EstrogenItems.LIQUID_ESTROGEN_BUCKET.get(),
                Component.translatable("advancement.estrogen.liquid_estrogen.title"),
                Component.translatable("advancement.estrogen.liquid_estrogen.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
        ).addCriterion("liquid_estrogen", InventoryChangeTrigger.TriggerInstance.hasItems(EstrogenItems.LIQUID_ESTROGEN_BUCKET.get()))
                .build(Estrogen.id("liquid_estrogen"));

        Advancement estrogenPill = Advancement.Builder.advancement().parent(liquidEstrogen).display(EstrogenItems.ESTROGEN_PILL.get(),
                Component.translatable("advancement.estrogen.estrogen_pill.title"),
                Component.translatable("advancement.estrogen.estrogen_pill.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
        ).addCriterion("estrogen_pill", InventoryChangeTrigger.TriggerInstance.hasItems(EstrogenItems.ESTROGEN_PILL.get()))
                .build(Estrogen.id("estrogen_pill"));

        Advancement estrogenPatches = Advancement.Builder.advancement().parent(liquidEstrogen).display(EstrogenItems.ESTROGEN_PATCHES.get().getFullStack(),
                Component.translatable("advancement.estrogen.estrogen_patches.title"),
                Component.translatable("advancement.estrogen.estrogen_patches.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
        ).addCriterion("estrogen_patches", InventoryChangeTrigger.TriggerInstance.hasItems(EstrogenItems.ESTROGEN_PATCHES.get()))
                .build(Estrogen.id("estrogen_patches"));

        Advancement uwu = Advancement.Builder.advancement().parent(root).display(EstrogenItems.UWU.get(),
                Component.translatable("advancement.estrogen.uwu.title"),
                Component.translatable("advancement.estrogen.uwu.description"),
                null,
                FrameType.CHALLENGE,
                true,
                true,
                true
        ).addCriterion("uwu", InventoryChangeTrigger.TriggerInstance.hasItems(EstrogenItems.UWU.get()))
                .build(Estrogen.id("uwu"));

        Advancement balls = Advancement.Builder.advancement().parent(root).display(EstrogenItems.BALLS.get(),
                Component.translatable("advancement.estrogen.balls.title"),
                Component.translatable("advancement.estrogen.balls.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                true
        ).addCriterion("balls", InventoryChangeTrigger.TriggerInstance.hasItems(EstrogenItems.BALLS.get()))
                .build(Estrogen.id("balls"));

        consumer.accept(root);
        consumer.accept(horseUrine);
        consumer.accept(usedFilter);
        consumer.accept(liquidEstrogen);
        consumer.accept(estrogenPill);
        consumer.accept(estrogenPatches);
        consumer.accept(uwu);
        consumer.accept(balls);
    }

    public static ItemPredicate getItems() {
        Collection<RegistryEntry<Item>> itemEntries = EstrogenItems.ITEMS.getEntries();
        ImmutableSet.Builder<Item> items = new ImmutableSet.Builder<>();
        for (RegistryEntry<Item> item : itemEntries) {
            items.add(item.get());
        }
        items.add(EstrogenBlocks.CENTRIFUGE.get().asItem());
        return  new ItemPredicate(null, items.build(),
                MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion)null, NbtPredicate.ANY);
    }
}
