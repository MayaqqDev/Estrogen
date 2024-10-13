package dev.mayaqq.estrogen.datagen.impl.translations;

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
        tb.add(EstrogenItems.MOTH_FUZZ.get(), "Moth Fuzz");
        tb.add(EstrogenEntities.MOTH.getSpawnEgg().get(), "Rosy Maple Moth Spawn Egg");
        tb.add(EstrogenItems.THIGH_HIGHS.get(), "Thigh Highs");
        tb.add(EstrogenItems.MOTH_ELYTRA.get(), "Rosy Maple Elytra");
        tb.add(EstrogenItems.GENDER_CHANGE_POTION.get(), "Gender Change Potion");
        // Potion
        tb.add("item.minecraft.tipped_arrow.effect.estrogen", "Arrow of Girl Power");
        tb.add("item.minecraft.potion.effect.estrogen", "Potion of Girl Power");
        tb.add("item.minecraft.splash_potion.effect.estrogen", "Splash Potion of Girl Power");
        tb.add("item.minecraft.lingering_potion.effect.estrogen", "Lingering Potion of Girl Power");
        // Buckets
        tb.add(EstrogenFluids.LIQUID_ESTROGEN.getBucket(), "Bucket of Liquid Estrogen");
        tb.add(EstrogenFluids.HORSE_URINE.getBucket(), "Bucket of Horse Urine");
        tb.add(EstrogenFluids.FILTRATED_HORSE_URINE.getBucket(), "Bucket of Filtrated Horse Urine");
        tb.add(EstrogenFluids.MOLTEN_SLIME.getBucket(), "Bucket of Molten Slime");
        tb.add(EstrogenFluids.MOLTEN_AMETHYST.getBucket(), "Bucket of Molten Amethyst");
        tb.add(EstrogenFluids.TESTOSTERONE_MIXTURE.getBucket(), "Bucket of Testosterone Mixture");

        // Entities
        tb.add(EstrogenEntities.MOTH.get(), "Rosy Maple Moth");

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
        tb.add("item.estrogen.thigh_highs.tooltip", "Thigh Highs");
        tb.add("item.estrogen.thigh_highs.tooltip.summary", "A pair of Thigh Highs that give you _Fall Damage Resistance_.");

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
        tb.add(EstrogenBlocks.MOTH_WOOL.get(), "Rosy Maple Wool");
        tb.add(EstrogenBlocks.QUILTED_MOTH_WOOL.get(), "Quilted Rosy Maple Wool");
        tb.add(EstrogenBlocks.MOTH_WOOL_CARPET.get(), "Rosy Maple Wool Carpet");
        tb.add(EstrogenBlocks.QUILTED_MOTH_WOOL_CARPET.get(), "Quilted Rosy Maple Wool Carpet");
        tb.add(EstrogenBlocks.MOTH_SEAT.get(), "Rosy Maple Seat");
        tb.add(EstrogenBlocks.MOTH_BED.get(), "Rosy Maple Bed");
        tb.add(EstrogenBlocks.QUILTED_MOTH_BED.get(), "Quilted Rosy Maple Bed");
        tb.add(EstrogenFluids.LIQUID_ESTROGEN.getBlock(), "Liquid Estrogen");
        tb.add(EstrogenFluids.HORSE_URINE.getBlock(), "Horse Urine");
        tb.add(EstrogenFluids.FILTRATED_HORSE_URINE.getBlock(), "Filtrated Horse Urine");
        tb.add(EstrogenFluids.MOLTEN_SLIME.getBlock(), "Molten Slime");
        tb.add(EstrogenFluids.MOLTEN_AMETHYST.getBlock(), "Molten Amethyst");
        tb.add(EstrogenFluids.TESTOSTERONE_MIXTURE.getBlock(), "Testosterone Mixture");

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
        tb.add("subtitles.estrogen.music.g03c", "erora - G03C");
        tb.add("subtitles.estrogen.music.trust_yourself", "erora - Trust Yourself");
        tb.add("subtitles.estrogen.music.amphitrite", "erora - Amphitrite");
        tb.add("subtitles.estrogen.music.aurum_berry", "erora - Aurum Berry");
        tb.add("subtitles.estrogen.music.inferred_dreams", "erora - Inferred Dreams");
        tb.add("subtitles.estrogen.music.sleeping_dreams", "erora - Sleeping Dreams");
        tb.add("subtitles.estrogen.music.inner_selfrealization", "erora - Inner Selfrealization");
        tb.add("subtitles.estrogen.cookie_jar.full", "Cookie Jar is full");
        tb.add("subtitles.estrogen.cookie_jar.insert", "Cookie Jar gets inserted into");
        tb.add("subtitles.estrogen.entity.moth.death", "Rosy Maple Moth dies");
        tb.add("subtitles.estrogen.entity.moth.hurt", "Rosy Maple Moth hurts");
        tb.add("subtitles.estrogen.entity.moth.loop", "Rosy Maple Moth buzzes");
        tb.add("subtitles.estrogen.entity.moth.fuzz_up", "Rosy Maple Moth fuzzes up");
        tb.add("subtitles.estrogen.dream_block.enter", "Dream Block entered");
        tb.add("subtitles.estrogen.dream_block.exit", "Dream Block exited");

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
        tb.add("tag.item.c.leather_items", "Leather Items");
        tb.add("tag.item.c.light_emitters", "Light Emitters");
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
        tb.add(EstrogenAttributes.FALL_DAMAGE_RESISTANCE.get(), "Fall Damage Resistance");

        // Display Sources
        tb.add("estrogen.display_source.entity_name", "Entity Name");

        // Mod Menu
        tb.add("estrogen.credits", "Credits & Contributors");

        // Curios :eyeroll:
        tb.add("curios.modifiers.thighs", "Thigh High Modifiers");

        // Thigh high tooltips - Cryptic asf because * colors * :)
        thighHigh(tb, "trans", "§bTr§dan§fsge§dnd§ber");
        thighHigh(tb, "pansexual", "§dPan§esex§bual");
        thighHigh(tb, "polysexual", "§dPoly§asex§bual");
        thighHigh(tb, "lesbian", "§cL§6es§fb§dia§5n");
        thighHigh(tb, "pride", "§cP§6r§ei§ad§5e");
        thighHigh(tb, "bisexual", "§dBis§5ex§1ual");
        thighHigh(tb, "asexual", "§5As§fex§8ual");
        thighHigh(tb, "aromantic", "§aAro§fman§8tic");
        thighHigh(tb, "genderqueer", "§aGen§fder§5queer");
        thighHigh(tb, "non_binary", "§eNon§f-Bi§5na§8ry");
        thighHigh(tb, "intersex", "§eInt§5er§esex");
        thighHigh(tb, "polyamorous", "§bPoly§fam§coro§8us");
        thighHigh(tb, "agender", "§8A§7g§fe§an§fd§7e§8r");
        thighHigh(tb, "vincian", "§3Vi§an§fc§bia§9n");
        thighHigh(tb, "genderfluid", "§dGe§fnd§5er§8fl§9uid");
        thighHigh(tb, "demisexual", "De§8mi§fse§5xu§fal");

        // Cosmetics
        tb.add("gui.estrogen.cosmetics.title", "Estrogen Cosmetics");
        tb.add("gui.estrogen.cosmetics.back", "Go Back");
        tb.add("gui.estrogen.cosmetics.close", "Close");
        tb.add("gui.estrogen.cosmetics.claim", "Claim Reward");
        tb.add("gui.estrogen.cosmetics.none", "None");
        tb.add("gui.estrogen.cosmetics.no_preview", "Cosmetics Preview only in-game");

        tb.add("gui.estrogen.cosmetics.login.description", """
            Welcome to Estrogen Cosmetics!
            
            To get started, please log in to your account.
            """);
        tb.add("gui.estrogen.cosmetics.login.button", "Login");
        tb.add("gui.estrogen.cosmetics.login.init", "Logging in...");
        tb.add("gui.estrogen.cosmetics.login.unauthorized", "Failed to login, check if you are logged in to Minecraft.");
        tb.add("gui.estrogen.cosmetics.login.server_error", "Error occurred on server side while logging in.");
        tb.add("gui.estrogen.cosmetics.login.failed", "Unknown error occurred while logging in.");

        tb.add("gui.estrogen.cosmetics.init", "Getting Cosmetics...");
        tb.add("gui.estrogen.cosmetics.unauthorized", "Failed to get cosmetics, try again later.");
        tb.add("gui.estrogen.cosmetics.server_error", "Error occurred on server side while getting cosmetics.");
        tb.add("gui.estrogen.cosmetics.failed", "Unknown error occurred while getting cosmetics.");

        tb.add("gui.estrogen.cosmetics.claim.description", """
            Welcome to Estrogen Cosmetics Reward Claim!
            
            Enter the code you received,
            into the text box below, to claim your cosmetic.
            """);
        tb.add("gui.estrogen.cosmetics.claim.button", "Claim");
        tb.add("gui.estrogen.cosmetics.claim.init", "Claiming...");
        tb.add("gui.estrogen.cosmetics.claim.forbidden", "Failed, code was already claimed.");
        tb.add("gui.estrogen.cosmetics.claim.not_found", "Failed, code is invalid.");
        tb.add("gui.estrogen.cosmetics.claim.failed", "Unknown error occurred while claiming.");
    }

    private void thighHigh(TranslationBuilder builder, String styleName, String value) {
        builder.add("tooltip.thigh_highs.estrogen." + styleName, value);
    }
}