package dev.mayaqq.estrogen.datagen.translations;

import dev.mayaqq.estrogen.registry.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EstrogenTranslations extends FabricLanguageProvider {

    public EstrogenTranslations(FabricDataOutput output) {
        super(output, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder tb) {
        // Status Effects
        tb.add(EstrogenEffects.ESTROGEN_EFFECT.get(), "Girl Power");
        tb.add(EstrogenEffects.ESTROGEN_EFFECT.get().getDescriptionId() + ".description", "Allows the player to dash and gives them some additional \"features\"");

        // Controls
        tb.add("category.estrogen", "Estrogen");
        tb.add("key.estrogen.dash", "Activate Dash");

        // Items
        tb.add("itemGroup.estrogen.estrogen", "Estrogen");
        tb.add(EstrogenItems.ESTROGEN_PILL.get(), "Estrogen Pill");
        tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Estrogen Patch");
        tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Incomplete Estrogen Patch");
        tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), "Crystal Estrogen Pill");
        tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get(), "Estrogen Chip Cookie");
        tb.add("item.estrogen.estrogen_chip_cookie.desc", "erora - G03C");
        tb.add(EstrogenItems.BALLS.get(), "Balls");
        tb.add(EstrogenItems.HORSE_URINE_BOTTLE.get(), "Horse Urine Bottle");
        tb.add(EstrogenItems.TESTOSTERONE_CHUNK.get(), "Testosterone Chunk");
        tb.add(EstrogenItems.TESTOSTERONE_POWDER.get(), "Testosterone Powder");
        tb.add(EstrogenItems.USED_FILTER.get(), "Used Filter");
        tb.add(EstrogenItems.UWU.get(), ":3");
        tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
        tb.add(EstrogenItems.INCOMPLETE_UWU.get(), "Incomplete UwU");
        tb.add(EstrogenItems.DREAM_BOTTLE.get(), "Bottle of Dreams");
        // Potion
        tb.add("item.minecraft.tipped_arrow.effect.estrogen", "Arrow of Girl Power");
        tb.add("item.minecraft.potion.effect.estrogen", "Potion of Girl Power");
        tb.add("item.minecraft.splash_potion.effect.estrogen", "Splash Potion of Girl Power");
        tb.add("item.minecraft.lingering_potion.effect.estrogen", "Lingering Potion of Girl Power");
        // Buckets
        tb.add(EstrogenItems.LIQUID_ESTROGEN_BUCKET.get(), "Bucket of Liquid Estrogen");
        tb.add(EstrogenItems.HORSE_URINE_BUCKET.get(), "Bucket of Horse Urine");
        tb.add(EstrogenItems.FILTRATED_HORSE_URINE_BUCKET.get(), "Bucket of Filtrated Horse Urine");
        tb.add(EstrogenItems.MOLTEN_SLIME_BUCKET.get(), "Bucket of Molten Slime");
        tb.add(EstrogenItems.MOLTEN_AMETHYST_BUCKET.get(), "Bucket of Molten Amethyst");
        tb.add(EstrogenItems.TESTOSTERONE_MIXTURE_BUCKET.get(), "Bucket of Testosterone Mixture");

        // Tooltip
        tb.add("item.estrogen.estrogen_pill.tooltip", "Estrogen Pill");
        tb.add("item.estrogen.estrogen_pill.tooltip.summary", "Temporary Source of _Girl Power_.");
        tb.add("item.estrogen.crystal_estrogen_pill.tooltip", "Crystal Estrogen Pill");
        tb.add("item.estrogen.crystal_estrogen_pill.tooltip.summary", "Temporary Source of _Girl Power_.");
        tb.add("item.estrogen.estrogen_patches.tooltip", "Estrogen Patches");
        tb.add("item.estrogen.estrogen_patches.tooltip.summary", "A refillable source of _Girl Power_.");
        tb.add("item.estrogen.estrogen_patches.tooltip.condition1", "When worn in the Thighs slot");
        tb.add("item.estrogen.estrogen_patches.tooltip.behaviour1", "Gives the _Girl Power Effect_ for as long as it's worn and is filled with _Liquid Estrogen_.");
        tb.add("block.estrogen.dormant_dream_block.tooltip", "Dormant Dream Block");
        tb.add("block.estrogen.dormant_dream_block.tooltip.summary", "A block with some _very_ powerful properties...");
        tb.add("block.estrogen.dormant_dream_block.tooltip.condition1", "When powered by a Redstone Signal");
        tb.add("block.estrogen.dormant_dream_block.tooltip.behaviour1", "At _night_, it will §kturn into its active form");

        tb.add("block.estrogen.cookie_jar.tooltip", "Cookie Jar");
        tb.add("block.estrogen.cookie_jar.tooltip.summary", "A jar to _store_ all your _Cookies_!");

        // Advancements
        tb.add("advancement.estrogen.root.title", "Create: Estrogen");
        tb.add("advancement.estrogen.root.description", "Create Addon Based around expressing yourself, fluid handling and expanding your factory!");
        tb.add("advancement.estrogen.horse_urine.title", "Ewww!");
        tb.add("advancement.estrogen.horse_urine.description", "Collect Horse Urine by \"milking\" a horse with a bottle.");
        tb.add("advancement.estrogen.used_filter.title", "First Step in Processing");
        tb.add("advancement.estrogen.used_filter.description", "Use a filter to remove the impurities from the Horse Urine you collected.");
        tb.add("advancement.estrogen.liquid_estrogen.title", "The Source of (Girl) Power");
        tb.add("advancement.estrogen.liquid_estrogen.description", "Collect a Bucket of Liquid Estrogen.");
        tb.add("advancement.estrogen.estrogen_pill.title", "Pill up, gals!");
        tb.add("advancement.estrogen.estrogen_pill.description", "Craft the elusive Estrogen Pill.");
        tb.add("advancement.estrogen.estrogen_patches.title", "(Almost) Permanent!");
        tb.add("advancement.estrogen.estrogen_patches.description", "Craft the Estrogen Patches and enjoy your long lasting femininity.");
        tb.add("advancement.estrogen.uwu.title", ":3");
        tb.add("advancement.estrogen.uwu.description", ":3333333333");
        tb.add("advancement.estrogen.balls.title", "Hehe");
        tb.add("advancement.estrogen.balls.description", "What happens when you split the Slime and the Balls?");
        tb.add("advancement.estrogen.cookie_jar.title", "Accept & Continue");
        tb.add("advancement.estrogen.cookie_jar.description", "This mod requires cookies to work properly. Please insert them into the jar");
        tb.add("advancement.estrogen.hard_to_catch.title", "Fact: Birds are hard to catch");
        tb.add("advancement.estrogen.hard_to_catch.description", "Wow. It's fast!");
        tb.add("advancement.estrogen.estrogen_dealer.title", "Estrogen Dealer");
        tb.add("advancement.estrogen.estrogen_dealer.description", "I am the one who knocks");

        // Blocks
        tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrifuge");
        tb.add(EstrogenBlocks.COOKIE_JAR.get(), "Cookie Jar");
        tb.add(EstrogenBlocks.DREAM_BLOCK.get(), "Dream Block");
        tb.add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get(), "Dormant Dream Block");
        tb.add(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get(), "Estrogen Pill Box");
        tb.add(EstrogenBlocks.LIQUID_ESTROGEN_BLOCK.get(), "Liquid Estrogen");
        tb.add(EstrogenBlocks.HORSE_URINE_BLOCK.get(), "Horse Urine");
        tb.add(EstrogenBlocks.FILTRATED_HORSE_URINE_BLOCK.get(), "Filtrated Horse Urine");
        tb.add(EstrogenBlocks.MOLTEN_SLIME_BLOCK.get(), "Molten Slime");
        tb.add(EstrogenBlocks.MOLTEN_AMETHYST_BLOCK.get(), "Molten Amethyst");
        tb.add(EstrogenBlocks.TESTOSTERONE_MIXTURE_BLOCK.get(), "Testosterone Mixture");

        // Fluids
        tb.add("fluid_type.estrogen.liquid_estrogen", "Liquid Estrogen");
        tb.add("fluid_type.estrogen.horse_urine", "Horse Urine");
        tb.add("fluid_type.estrogen.filtrated_horse_urine", "Filtrated Horse Urine");
        tb.add("fluid_type.estrogen.molten_slime", "Molten Slime");
        tb.add("fluid_type.estrogen.molten_amethyst", "Molten Amethyst");
        tb.add("fluid_type.estrogen.testosterone_mixture", "Testosterone Mixture");
        tb.add("fluid.estrogen.liquid_estrogen", "Liquid Estrogen");
        tb.add("fluid.estrogen.horse_urine", "Horse Urine");
        tb.add("fluid.estrogen.filtrated_horse_urine", "Filtrated Horse Urine");
        tb.add("fluid.estrogen.molten_slime", "Molten Slime");
        tb.add("fluid.estrogen.molten_amethyst", "Molten Amethyst");
        tb.add("fluid.estrogen.testosterone_mixture", "Testosterone Mixture");

        // Sounds
        tb.add("subtitles.estrogen.dash", "Girl Power Dash");

        // Death
        tb.add("death.attack.girlpower", "%s Girlbossed too hard");
        tb.add("death.attack.girlpower.player", "%s Girlbossed too hard");

        // Trinkets
        tb.add("trinkets.slot.legs.thighs", "Thighs");
        // Curios
        tb.add("curios.identifier.thighs", "Thighs");

        // Recipe Viewers
        tb.add("create.recipe.centrifuging", "Centrifuging");
        tb.add("create.recipe.entity_interaction", "Entity Interaction");
        tb.add("recipe.entity_interaction.cant_be_baby", "This entity can't be a baby");

        // Enchantments
        tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "Curse of Uwufying");
        tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUfies your chat messages >///<");

        // EMI
        tb.add("emi.category.estrogen.centrifuging", "Centrifuging");
        tb.add("emi.category.estrogen.entity_interaction", "Entity Interaction");

        // Tags
        // Items
        tb.add("tag.item.trinkets.legs.thighs", "Thighs");
        tb.add("tag.item.estrogen.uwufying", "Uwufying");
        tb.add("tag.item.curios.thighs", "Thighs");
        tb.add("tag.item.c.cookies", "Cookies");
        // Fluids
        tb.add("tag.fluid.estrogen.urine", "Urine");

        // Ponder
        // Centrifuge
        tb.add("estrogen.ponder.intro.header", "The Centrifuge Requirements");
        tb.add("estrogen.ponder.intro.text_1", "The Centrifuge needs the maximum speed (256 RPM) to work!");
        tb.add("estrogen.ponder.basic.header", "How to use the Centrifuge");
        tb.add("estrogen.ponder.basic.text_1", "The Centrifuge doesn't have any inventory, you will need to place fluid containers around it to make it work!");
        tb.add("estrogen.ponder.basic.text_2", "You can input fluids from the bottom");
        tb.add("estrogen.ponder.basic.text_3", "And output fluids from the top");

        // Attributes
        tb.add(EstrogenAttributes.DASH_LEVEL.get(), "Dash Level");
        tb.add(EstrogenAttributes.BOOB_GROWING_START_TIME.get(), "Upper Body Start Time");
        tb.add(EstrogenAttributes.BOOB_INITIAL_SIZE.get(), "Upper Body initial size");
    }
}