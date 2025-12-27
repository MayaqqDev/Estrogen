package dev.mayaqq.estrogen.datagen.impl.advancements

import dev.mayaqq.estrogen.Estrogen
import dev.mayaqq.estrogen.content.EstrogenBlocks
import dev.mayaqq.estrogen.content.EstrogenEffects
import dev.mayaqq.estrogen.content.EstrogenFluids
import dev.mayaqq.estrogen.content.EstrogenItems
import dev.mayaqq.estrogen.content.advancements.triggers.InsertJarTrigger
import dev.mayaqq.estrogen.content.advancements.triggers.KilledWithEffectTrigger
import dev.mayaqq.estrogen.id
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.FrameType
import net.minecraft.advancements.critereon.*
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance.hasItems
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Items
import uwu.serenity.kritter.entries
import java.util.function.Consumer


class EstrogenAdvancements(output: FabricDataOutput) : FabricAdvancementProvider(output) {
    override fun generateAdvancement(consumer: Consumer<Advancement>) {
        val root: Advancement = Advancement.Builder.advancement()
            .display(
                EstrogenItems.EstrogenPill,
                Component.translatable("advancement.estrogen.root.title"),
                Component.translatable("advancement.estrogen.root.description"),
                id("textures/block/dream_block/particle.png"),
                FrameType.TASK,
                true,
                true,
                false
            ).addCriterion("root", hasItems(getItems()))
            .build(id("root"))

        val horseUrine: Advancement = Advancement.Builder.advancement()
            .parent(root)
            .display(
                EstrogenItems.HorseUrineBottle,
                Component.translatable("advancement.estrogen.horse_urine.title"),
                Component.translatable("advancement.estrogen.horse_urine.description"),
                null,
                FrameType.TASK,
                true,
                true,
                false
            ).addCriterion("horse_urine", hasItems(EstrogenItems.HorseUrineBottle))
            .build(id("horse_urine"))

        val wetSponge: Advancement = Advancement.Builder.advancement()
            .parent(horseUrine)
            .display(
                EstrogenFluids.FiltratedHorseUrine.bucket,
                Component.translatable("advancement.estrogen.wet_sponge.title"),
                Component.translatable("advancement.estrogen.wet_sponge.description"),
                null,
                FrameType.TASK,
                true,
                true,
                false
            ).addCriterion("wet_sponge", hasItems(EstrogenFluids.FiltratedHorseUrine.bucket))
            .build(id("wet_sponge"))

        val liquidEstrogen: Advancement = Advancement.Builder.advancement()
            .parent(wetSponge)
            .display(
                EstrogenFluids.LiquidEstrogen.bucket,
                Component.translatable("advancement.estrogen.liquid_estrogen.title"),
                Component.translatable("advancement.estrogen.liquid_estrogen.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
            ).addCriterion("liquid_estrogen", hasItems(EstrogenFluids.LiquidEstrogen.bucket))
            .build(id("liquid_estrogen"))

        val estrogenPill: Advancement = Advancement.Builder.advancement()
            .parent(liquidEstrogen)
            .display(
                EstrogenItems.EstrogenPill,
                Component.translatable("advancement.estrogen.estrogen_pill.title"),
                Component.translatable("advancement.estrogen.estrogen_pill.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
            ).addCriterion("estrogen_pill", hasItems(EstrogenItems.EstrogenPill))
            .build(id("estrogen_pill"))

        val estrogenPatches: Advancement = Advancement.Builder.advancement()
            .parent(liquidEstrogen)
            .display(
                EstrogenItems.EstrogenPatches,
                Component.translatable("advancement.estrogen.estrogen_patches.title"),
                Component.translatable("advancement.estrogen.estrogen_patches.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
            ).addCriterion("estrogen_patches", hasItems(EstrogenItems.EstrogenPatches))
            .build(id("estrogen_patches"))

        val uwu: Advancement = Advancement.Builder.advancement()
            .parent(root)
            .display(
                EstrogenItems.ColonThree,
                Component.translatable("advancement.estrogen.uwu.title"),
                Component.translatable("advancement.estrogen.uwu.description"),
                null,
                FrameType.CHALLENGE,
                true,
                true,
                true
            ).addCriterion("uwu", hasItems(EstrogenItems.ColonThree))
            .build(id("uwu"))

        val balls: Advancement = Advancement.Builder.advancement()
            .parent(root)
            .display(
                EstrogenItems.Balls,
                Component.translatable("advancement.estrogen.balls.title"),
                Component.translatable("advancement.estrogen.balls.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                true
            ).addCriterion("balls", hasItems(EstrogenItems.Balls))
            .build(id("balls"))

        val cookie_jar: Advancement = Advancement.Builder.advancement()
            .parent(estrogenPill)
            .display(
                EstrogenBlocks.CookieJar,
                Component.translatable("advancement.estrogen.cookie_jar.title"),
                Component.translatable("advancement.estrogen.cookie_jar.description"),
                null,
                FrameType.GOAL,
                true,
                true,
                false
            ).addCriterion("cookie_jar_place", InsertJarTrigger.TriggerInstance.insertJar())
            .build(id("cookie_jar"))

        val hard_to_catch: Advancement = Advancement.Builder.advancement()
            .parent(estrogenPill)
            .display(
                Items.PHANTOM_MEMBRANE,
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
                    EstrogenEffects.Estrogen, ContextAwarePredicate.ANY
                )
            )
            .build(id("hard_to_catch"))

        val estrogen_dealer: Advancement = Advancement.Builder.advancement()
            .parent(estrogenPill)
            .display(
                Items.SUGAR,
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
                    ItemPredicate.Builder.item().of(EstrogenItems.EstrogenPill, EstrogenItems.CrystalEstrogenPill)
                        .build(),
                    ContextAwarePredicate.ANY
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
        return ItemPredicate(
            null, Estrogen.entries(Registries.ITEM).map { entry -> entry.value }.toSet(),
            MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE,
            null, NbtPredicate.ANY
        )
    }
}