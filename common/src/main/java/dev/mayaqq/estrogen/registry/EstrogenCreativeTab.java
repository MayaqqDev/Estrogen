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
import static dev.mayaqq.estrogen.registry.EstrogenItems.*;

public class EstrogenCreativeTab {

    public static final CreativeTabRegistrar TAB = CreativeTabRegistrar.create(Estrogen.MOD_ID);

    public static final RegistryEntry<CreativeModeTab> MAIN = TAB.entry("estrogen")
        .icon(ESTROGEN_PILL::asStack)
        .displayItems(EstrogenCreativeTab::creativeTabItems)
        .register();

    public static void creativeTabItems(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
        output.accept(ESTROGEN_PILL.asStack());
        output.accept(CRYSTAL_ESTROGEN_PILL.asStack());
        output.accept(BALLS.asStack());
        output.accept(MOTH_FUZZ.asStack());
        output.accept(TESTOSTERONE_CHUNK.asStack());
        output.accept(TESTOSTERONE_POWDER.asStack());
        output.accept(USED_FILTER.asStack());
        output.accept(ESTROGEN_CHIP_COOKIE.asStack());
        output.accept(HORSE_URINE_BOTTLE.asStack());
        output.accept(ESTROGEN_PATCHES.get().getFullStack());
        output.accept(ESTROGEN_PATCHES.asStack());
        output.accept(THIGH_HIGHS.asStack());
        output.accept(MOTH_ELYTRA.asStack());
        output.accept(UWU.asStack());
        output.accept(CENTRIFUGE.asStack());
        output.accept(COOKIE_JAR.asStack());
        output.accept(DREAM_BLOCK.asStack());
        output.accept(DORMANT_DREAM_BLOCK.asStack());
        output.accept(ESTROGEN_PILL_BLOCK.asStack());
        output.accept(MOTH_WOOL.asStack());
        output.accept(QUILTED_MOTH_WOOL.asStack());
        output.accept(MOTH_WOOL_CARPET.asStack());
        output.accept(QUILTED_MOTH_WOOL_CARPET.asStack());
        output.accept(MOTH_SEAT.asStack());
        output.accept(tippedArrow(EstrogenPotions.ESTROGEN_POTION.get()));
        output.accept(MOLTEN_SLIME_BUCKET.get().getDefaultInstance());
        output.accept(TESTOSTERONE_MIXTURE_BUCKET.get().getDefaultInstance());
        output.accept(LIQUID_ESTROGEN_BUCKET.get().getDefaultInstance());
        output.accept(FILTRATED_HORSE_URINE_BUCKET.get().getDefaultInstance());
        output.accept(HORSE_URINE_BUCKET.get().getDefaultInstance());
        output.accept(MOLTEN_AMETHYST_BUCKET.get().getDefaultInstance());
        output.accept(EstrogenEntities.MOTH.getSpawnEgg().asStack());
        THIGH_HIGHS.get().streamStyleItems().forEach(output::accept);
    }

    public void init() {}

    public static ItemStack tippedArrow(Potion potion) {
        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.setPotion(stack, potion);
        return stack;
    }
}
