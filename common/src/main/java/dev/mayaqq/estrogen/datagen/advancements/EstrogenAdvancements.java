package dev.mayaqq.estrogen.datagen.advancements;

import com.google.common.collect.ImmutableSet;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
                new ResourceLocation("textures/block/sculk_catalyst_top.png"),
                FrameType.GOAL,
                true,
                true,
                false
        ).addCriterion("root", InventoryChangeTrigger.TriggerInstance.hasItems(getItems()))
                .build(Estrogen.id("root"));

        consumer.accept(root);
    }

    public static ItemPredicate getItems() {
        Collection<RegistryEntry<Item>> itemEntries = EstrogenItems.ITEMS.getEntries();
        ImmutableSet.Builder<Item> items = new ImmutableSet.Builder<>();
        for (RegistryEntry<Item> item : itemEntries) {
            items.add(item.get());
        }
        return  new ItemPredicate(null, items.build(),
                MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, (Potion)null, NbtPredicate.ANY);
    }
}
