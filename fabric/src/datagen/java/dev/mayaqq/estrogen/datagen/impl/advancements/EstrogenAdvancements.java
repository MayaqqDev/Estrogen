package dev.mayaqq.estrogen.datagen.impl.advancements;

import com.google.common.collect.ImmutableSet;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.advancements.triggers.InsertJarTrigger;
import dev.mayaqq.estrogen.registry.advancements.triggers.KilledWithEffectTrigger;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import uwu.serenity.critter.api.entry.RegistryEntry;

import java.util.Collection;
import java.util.function.Consumer;

import static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems;

public class EstrogenAdvancements extends FabricAdvancementProvider {
    public EstrogenAdvancements(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement()
                .display(EstrogenItems.ESTROGEN_PILL,
                        Component.translatable("advancement.estrogen.root.title"),
                        Component.translatable("advancement.estrogen.root.description"),
                        Estrogen.id("textures/block/dream_block/particle.png"),
                        FrameType.TASK,
                        true,
                        true,
                        false
                ).addCriterion("root", hasItems(getItems()))
                .build(Estrogen.id("root"));

        Advancement horseUrine = Advancement.Builder.advancement()
                .parent(root)
                .display(EstrogenItems.HORSE_URINE_BOTTLE,
                        Component.translatable("advancement.estrogen.horse_urine.title"),
                        Component.translatable("advancement.estrogen.horse_urine.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                ).addCriterion("horse_urine", hasItems(EstrogenItems.HORSE_URINE_BOTTLE))
                .build(Estrogen.id("horse_urine"));

        Advancement usedFilter = Advancement.Builder.advancement()
                .parent(horseUrine)
                .display(EstrogenItems.USED_FILTER,
                        Component.translatable("advancement.estrogen.used_filter.title"),
                        Component.translatable("advancement.estrogen.used_filter.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                ).addCriterion("used_filter", hasItems(EstrogenItems.USED_FILTER))
                .build(Estrogen.id("used_filter"));

        Advancement liquidEstrogen = Advancement.Builder.advancement()
                .parent(usedFilter)
                .display(EstrogenFluids.LIQUID_ESTROGEN.getBucket(),
                        Component.translatable("advancement.estrogen.liquid_estrogen.title"),
                        Component.translatable("advancement.estrogen.liquid_estrogen.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                ).addCriterion("liquid_estrogen", hasItems(EstrogenFluids.LIQUID_ESTROGEN.getBucket()))
                .build(Estrogen.id("liquid_estrogen"));

        Advancement estrogenPill = Advancement.Builder.advancement()
                .parent(liquidEstrogen)
                .display(EstrogenItems.ESTROGEN_PILL.get(),
                        Component.translatable("advancement.estrogen.estrogen_pill.title"),
                        Component.translatable("advancement.estrogen.estrogen_pill.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                ).addCriterion("estrogen_pill", hasItems(EstrogenItems.ESTROGEN_PILL))
                .build(Estrogen.id("estrogen_pill"));

        Advancement estrogenPatches = Advancement.Builder.advancement()
                .parent(liquidEstrogen)
                .display(EstrogenItems.ESTROGEN_PATCHES.get().getFullStack(),
                        Component.translatable("advancement.estrogen.estrogen_patches.title"),
                        Component.translatable("advancement.estrogen.estrogen_patches.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                ).addCriterion("estrogen_patches", hasItems(EstrogenItems.ESTROGEN_PATCHES))
                .build(Estrogen.id("estrogen_patches"));

        Advancement uwu = Advancement.Builder.advancement()
                .parent(root)
                .display(EstrogenItems.UWU,
                        Component.translatable("advancement.estrogen.uwu.title"),
                        Component.translatable("advancement.estrogen.uwu.description"),
                        null,
                        FrameType.CHALLENGE,
                        true,
                        true,
                        true
                ).addCriterion("uwu", hasItems(EstrogenItems.UWU))
                .build(Estrogen.id("uwu"));

        Advancement balls = Advancement.Builder.advancement()
                .parent(root)
                .display(EstrogenItems.BALLS,
                        Component.translatable("advancement.estrogen.balls.title"),
                        Component.translatable("advancement.estrogen.balls.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        true
                ).addCriterion("balls", hasItems(EstrogenItems.BALLS))
                .build(Estrogen.id("balls"));

        Advancement cookie_jar = Advancement.Builder.advancement()
                .parent(estrogenPill)
                .display(EstrogenBlocks.COOKIE_JAR,
                        Component.translatable("advancement.estrogen.cookie_jar.title"),
                        Component.translatable("advancement.estrogen.cookie_jar.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                ).addCriterion("cookie_jar_place", InsertJarTrigger.TriggerInstance.insertJar())
                .build(Estrogen.id("cookie_jar"));

        Advancement hard_to_catch = Advancement.Builder.advancement()
                .parent(estrogenPill)
                .display(Items.PHANTOM_MEMBRANE,
                        Component.translatable("advancement.estrogen.hard_to_catch.title"),
                        Component.translatable("advancement.estrogen.hard_to_catch.description"),
                        null,
                        FrameType.CHALLENGE,
                        true,
                        true,
                        true
                ).addCriterion(
                        "hard_to_catch",
                        KilledWithEffectTrigger.TriggerInstance.killedWithEffect(
                                EntityPredicate.wrap(
                                        EntityPredicate.Builder.entity()
                                                .entityType(EntityTypePredicate.of(EntityType.PHANTOM))
                                                .build()
                                ),
                                EstrogenEffects.ESTROGEN_EFFECT.get(), ContextAwarePredicate.ANY
                        )
                )
                .build(Estrogen.id("hard_to_catch"));

        Advancement estrogen_dealer = Advancement.Builder.advancement()
                .parent(estrogenPill)
                .display(Items.SUGAR,
                        Component.translatable("advancement.estrogen.estrogen_dealer.title"),
                        Component.translatable("advancement.estrogen.estrogen_dealer.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                ).addCriterion(
                        "estrogen_dealer",
                        PickedUpItemTrigger.TriggerInstance.thrownItemPickedUpByPlayer(
                                ContextAwarePredicate.ANY,
                                ItemPredicate.Builder.item().of(EstrogenItems.ESTROGEN_PILL, EstrogenItems.CRYSTAL_ESTROGEN_PILL).build(),
                                ContextAwarePredicate.ANY
                        )
                )
                .build(Estrogen.id("estrogen_dealer"));

        consumer.accept(root);
        consumer.accept(horseUrine);
        consumer.accept(usedFilter);
        consumer.accept(liquidEstrogen);
        consumer.accept(estrogenPill);
        consumer.accept(estrogenPatches);
        consumer.accept(uwu);
        consumer.accept(balls);
        consumer.accept(cookie_jar);
        consumer.accept(hard_to_catch);
        consumer.accept(estrogen_dealer);
    }

    public static ItemPredicate getItems() {
        Collection<RegistryEntry<? extends Item>> itemEntries = Estrogen.REGISTRIES.getAllEntries(Registries.ITEM);
        ImmutableSet.Builder<Item> items = new ImmutableSet.Builder<>();
        for (RegistryEntry<? extends Item> item : itemEntries) {
            items.add(item.get());
        }
        return new ItemPredicate(
                null, items.build(),
                MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE,
                null, NbtPredicate.ANY
        );
    }
}
