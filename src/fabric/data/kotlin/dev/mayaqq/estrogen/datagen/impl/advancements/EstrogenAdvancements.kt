package dev.mayaqq.estrogen.datagen.impl.advancements

import dev.mayaqq.estrogen.MOD_ID
import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.content.advancements.triggers.InsertJarTrigger
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectTrigger
import dev.mayaqq.estrogen.id
import dev.mayaqq.estrogen.utils.holder
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.AdvancementType
import net.minecraft.advancements.critereon.*
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems
import net.minecraft.core.HolderLookup
import net.minecraft.core.HolderSet
import net.minecraft.core.component.DataComponentPredicate
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import kotlin.jvm.optionals.getOrNull


class EstrogenAdvancements(output: FabricDataOutput, lookup: CompletableFuture<HolderLookup.Provider>) : FabricAdvancementProvider(output, lookup) {
    override fun generateAdvancement(lookup: HolderLookup.Provider, consumer: Consumer<AdvancementHolder>) {
        val root: AdvancementHolder = Advancement.Builder.advancement()
            .display(
                EstrogenItems.EstrogenPill,
                Component.translatable("advancement.estrogen.root.title"),
                Component.translatable("advancement.estrogen.root.description"),
                id("textures/block/dream_block/particle.png"),
                AdvancementType.TASK,
                true,
                true,
                false
            ).addCriterion("root", hasItems(getItems()))
            .build(id("root"))

        val horseUrine: AdvancementHolder = Advancement.Builder.advancement()
            .parent(root)
            .display(
                EstrogenItems.HorseUrineBottle,
                Component.translatable("advancement.estrogen.horse_urine.title"),
                Component.translatable("advancement.estrogen.horse_urine.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            ).addCriterion("horse_urine", hasItems(EstrogenItems.HorseUrineBottle))
            .build(id("horse_urine"))

        val wetSponge: AdvancementHolder = Advancement.Builder.advancement()
            .parent(horseUrine)
            .display(
                EstrogenFluids.FiltratedHorseUrine.bucket,
                Component.translatable("advancement.estrogen.wet_sponge.title"),
                Component.translatable("advancement.estrogen.wet_sponge.description"),
                null,
                AdvancementType.TASK,
                true,
                true,
                false
            ).addCriterion("wet_sponge", hasItems(EstrogenFluids.FiltratedHorseUrine.bucket))
            .build(id("wet_sponge"))

        val liquidEstrogen: AdvancementHolder = Advancement.Builder.advancement()
            .parent(wetSponge)
            .display(
                EstrogenFluids.LiquidEstrogen.bucket,
                Component.translatable("advancement.estrogen.liquid_estrogen.title"),
                Component.translatable("advancement.estrogen.liquid_estrogen.description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
            ).addCriterion("liquid_estrogen", hasItems(EstrogenFluids.LiquidEstrogen.bucket))
            .build(id("liquid_estrogen"))

        val estrogenPill: AdvancementHolder = Advancement.Builder.advancement()
            .parent(liquidEstrogen)
            .display(
                EstrogenItems.EstrogenPill,
                Component.translatable("advancement.estrogen.estrogen_pill.title"),
                Component.translatable("advancement.estrogen.estrogen_pill.description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
            ).addCriterion("estrogen_pill", hasItems(EstrogenItems.EstrogenPill))
            .build(id("estrogen_pill"))

        val estrogenPatches: AdvancementHolder = Advancement.Builder.advancement()
            .parent(liquidEstrogen)
            .display(
                EstrogenItems.EstrogenPatches,
                Component.translatable("advancement.estrogen.estrogen_patches.title"),
                Component.translatable("advancement.estrogen.estrogen_patches.description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
            ).addCriterion("estrogen_patches", hasItems(EstrogenItems.EstrogenPatches))
            .build(id("estrogen_patches"))

        val uwu: AdvancementHolder = Advancement.Builder.advancement()
            .parent(root)
            .display(
                EstrogenItems.ColonThree,
                Component.translatable("advancement.estrogen.uwu.title"),
                Component.translatable("advancement.estrogen.uwu.description"),
                null,
                AdvancementType.CHALLENGE,
                true,
                true,
                true
            ).addCriterion("uwu", hasItems(EstrogenItems.ColonThree))
            .build(id("uwu"))

        val balls: AdvancementHolder = Advancement.Builder.advancement()
            .parent(root)
            .display(
                EstrogenItems.Balls,
                Component.translatable("advancement.estrogen.balls.title"),
                Component.translatable("advancement.estrogen.balls.description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                true
            ).addCriterion("balls", hasItems(EstrogenItems.Balls))
            .build(id("balls"))

        val cookie_jar: AdvancementHolder = Advancement.Builder.advancement()
            .parent(estrogenPill)
            .display(
                EstrogenBlocks.CookieJar.value!!,
                Component.translatable("advancement.estrogen.cookie_jar.title"),
                Component.translatable("advancement.estrogen.cookie_jar.description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
            ).addCriterion("cookie_jar_place", InsertJarTrigger.TriggerInstance.insertJar())
            .build(id("cookie_jar"))

        val hard_to_catch: AdvancementHolder = Advancement.Builder.advancement()
            .parent(estrogenPill)
            .display(
                Items.PHANTOM_MEMBRANE,
                Component.translatable("advancement.estrogen.hard_to_catch.title"),
                Component.translatable("advancement.estrogen.hard_to_catch.description"),
                null,
                AdvancementType.CHALLENGE,
                true,
                true,
                true
            ).addCriterion(
                "hard_to_catch",
                AdvancementTriggers.KilledWithEffect.createCriterion(
                    KilledWithEffectTrigger.TriggerInstance.killedWithEffect(
                        EntityPredicate.wrap(
                            EntityPredicate.Builder.entity()
                                .entityType(EntityTypePredicate.of(EntityType.PHANTOM))
                                .build()
                        ),
                        EstrogenEffects.Estrogen.holder(), ContextAwarePredicate.create()
                    )
                )
            )
            .build(id("hard_to_catch"))

        val estrogen_dealer: AdvancementHolder = Advancement.Builder.advancement()
            .parent(estrogenPill)
            .display(
                Items.SUGAR,
                Component.translatable("advancement.estrogen.estrogen_dealer.title"),
                Component.translatable("advancement.estrogen.estrogen_dealer.description"),
                null,
                AdvancementType.GOAL,
                true,
                true,
                false
            ).addCriterion(
                "estrogen_dealer",
                PickedUpItemTrigger.TriggerInstance.thrownItemPickedUpByPlayer(
                    ContextAwarePredicate.create().optional(),
                    ItemPredicate.Builder.item().of(EstrogenItems.EstrogenPill, EstrogenItems.CrystalEstrogenPill).build().optional(),
                    ContextAwarePredicate.create().optional()
                )
            )
            .build(id("estrogen_dealer"))

        consumer.accept(root)
        consumer.accept(horseUrine)
        consumer.accept(wetSponge)
        consumer.accept(liquidEstrogen)
        consumer.accept(estrogenPill)
        consumer.accept(estrogenPatches)
        consumer.accept(uwu)
        consumer.accept(balls)
        consumer.accept(cookie_jar)
        consumer.accept(hard_to_catch)
        consumer.accept(estrogen_dealer)
    }

    fun getItems(): ItemPredicate {
        return ItemPredicate(HolderSet.direct(BuiltInRegistries.ITEM.registryKeySet().filter {
                it.location().namespace == MOD_ID
            }.map { BuiltInRegistries.ITEM.getHolder(it).getOrNull()!! }).optional(),
        MinMaxBounds.Ints.ANY,
        DataComponentPredicate.EMPTY,
        mapOf()
        )
    }

    private fun <T : Any> T.optional(): Optional<T> = Optional.of(this)
}