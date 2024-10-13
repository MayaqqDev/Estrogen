package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.stdlib.creativeTabs.CreativeTabRegistrar;

import static dev.mayaqq.estrogen.registry.EstrogenBlocks.*;
import static dev.mayaqq.estrogen.registry.EstrogenEntities.MOTH;
import static dev.mayaqq.estrogen.registry.EstrogenFluids.*;
import static dev.mayaqq.estrogen.registry.EstrogenItems.*;

public class EstrogenCreativeTab {

    public static final CreativeTabRegistrar TAB = CreativeTabRegistrar.create(Estrogen.REGISTRIES);

    public static final RegistryEntry<CreativeModeTab> MAIN = TAB.entry("estrogen")
        .icon(ESTROGEN_PILL::asStack)
        .displayItems(EstrogenCreativeTab::creativeTabItems)
        .register();

    public static void creativeTabItems(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
        output.accept(ESTROGEN_PILL);
        output.accept(CRYSTAL_ESTROGEN_PILL);
        output.accept(GENDER_CHANGE_POTION);
        output.accept(BALLS);
        output.accept(MOTH_FUZZ);
        output.accept(TESTOSTERONE_CHUNK);
        output.accept(TESTOSTERONE_POWDER);
        output.accept(USED_FILTER);
        output.accept(ESTROGEN_CHIP_COOKIE);
        output.accept(HORSE_URINE_BOTTLE);
        output.accept(ESTROGEN_PATCHES.get().getFullStack());
        output.accept(ESTROGEN_PATCHES);
        output.accept(THIGH_HIGHS);
        output.accept(MOTH_ELYTRA);
        output.accept(UWU);
        output.accept(CENTRIFUGE);
        output.accept(COOKIE_JAR);
        output.accept(DREAM_BLOCK);
        output.accept(DORMANT_DREAM_BLOCK);
        output.accept(ESTROGEN_PILL_BLOCK);
        output.accept(MOTH_WOOL);
        output.accept(QUILTED_MOTH_WOOL);
        output.accept(MOTH_WOOL_CARPET);
        output.accept(QUILTED_MOTH_WOOL_CARPET);
        output.accept(MOTH_SEAT);
        output.accept(MOTH_BED);
        output.accept(QUILTED_MOTH_BED);
        output.accept(tippedArrow(EstrogenPotions.ESTROGEN_POTION.get()));
        output.accept(MOLTEN_SLIME.getBucket());
        output.accept(TESTOSTERONE_MIXTURE.getBucket());
        output.accept(LIQUID_ESTROGEN.getBucket());
        output.accept(FILTRATED_HORSE_URINE.getBucket());
        output.accept(HORSE_URINE.getBucket());
        output.accept(MOLTEN_AMETHYST.getBucket());
        output.accept(MOTH.getSpawnEgg());
        THIGH_HIGHS.get().streamStyleItems().forEach(output::accept);
    }

    public static ItemStack tippedArrow(Potion potion) {
        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.setPotion(stack, potion);
        return stack;
    }
}
