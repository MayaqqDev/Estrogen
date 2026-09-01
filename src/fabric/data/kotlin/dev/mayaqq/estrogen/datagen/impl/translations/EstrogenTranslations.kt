package dev.mayaqq.estrogen.datagen.impl.translations

import dev.mayaqq.estrogen.content.*
import dev.mayaqq.estrogen.id
import invoke.kitty.kritter.registry.api.entry.holder
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import java.util.concurrent.CompletableFuture

class EstrogenTranslations(output: FabricDataOutput, lookup: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(output, "en_us", lookup) {

    override fun generateTranslations(provider: HolderLookup.Provider, tb: TranslationBuilder) {
        // Status Effects
        tb.add(EstrogenEffects.Estrogen.value, "Girl Power")
        tb.add(
            EstrogenEffects.Estrogen.value?.descriptionId + ".description",
            "Allows the player to dash and gives them some additional \"features\""
        )
        tb.add(EstrogenEffects.Dreaming.value, "Dreaming")
        tb.add(
            EstrogenEffects.Dreaming.value?.descriptionId + ".description",
            "Triggers when sleeping near Dormant Dream Blocks without a Dreamcatcher nearby"
        )

        // Controls
        tb.add("category.estrogen", "Estrogen")
        tb.add("key.estrogen.dash", "Activate Dash")

        // Items
        tb.add("itemGroup.estrogen.estrogen", "Estrogen")
        tb.add(EstrogenItems.EstrogenPill.value, "Estrogen Pill")
        tb.add(EstrogenItems.EstrogenPatch.value, "Estrogen Patch")
        tb.add(EstrogenItems.CrystalEstrogenPill.value, "Crystal Estrogen Pill")
        tb.add(EstrogenItems.EstrogenChipCookie.value, "Estrogen Chip Cookie")
        tb.add("item.estrogen.estrogen_chip_cookie.desc", "erora - G03C")
        tb.add(EstrogenItems.Balls.value, "Balls")
        tb.add(EstrogenItems.HorseUrineBottle.value, "Horse Urine Bottle")
        tb.add(EstrogenItems.TestosteroneChunk.value, "Testosterone Chunk")
        tb.add(EstrogenItems.TestosteronePowder.value, "Testosterone Powder")
        tb.add(EstrogenItems.ColonThree.value, ":3")
        tb.add(EstrogenItems.DreamBottle, "Bottle of Dreams")
        tb.add(EstrogenItems.MothFuzz.value, "Moth Fuzz")
        tb.add(BuiltInRegistries.ITEM.get(id("moth_spawn_egg")), "Rosy Maple Moth Spawn Egg")
        tb.add(EstrogenItems.ThighHighs.value, "Thigh Highs")
        tb.add("estrogen.item.dyeable", "Dyeable")
        tb.add(EstrogenItems.MothElytra.value, "Rosy Maple Elytra")
        tb.add(EstrogenItems.GenderChangePotion.value, "Gender Change Potion")
        tb.add("estrogen.item.transfer_dummy", "Create: Estrogen item")
        // Potion
        tb.add("item.minecraft.tipped_arrow.effect.estrogen", "Arrow of Girl Power")
        tb.add("item.minecraft.potion.effect.estrogen", "Potion of Girl Power")
        tb.add("item.minecraft.splash_potion.effect.estrogen", "Splash Potion of Girl Power")
        tb.add("item.minecraft.lingering_potion.effect.estrogen", "Lingering Potion of Girl Power")
        // Buckets
        tb.add(EstrogenFluids.LiquidEstrogen.bucket, "Bucket of Liquid Estrogen")
        tb.add(EstrogenFluids.HorseUrine.bucket, "Bucket of Horse Urine")
        tb.add(EstrogenFluids.FiltratedHorseUrine.bucket, "Bucket of Filtrated Horse Urine")
        tb.add(EstrogenFluids.MoltenSlime.bucket, "Bucket of Molten Slime")
        tb.add(EstrogenFluids.MoltenAmethyst.bucket, "Bucket of Molten Amethyst")
        tb.add(EstrogenFluids.TestosteroneMixture.bucket, "Bucket of Testosterone Mixture")
        tb.add(EstrogenFluids.GenderFluid.bucket, "Bucket of Gender Fluid")

        // Entities
        tb.add(EstrogenEntities.Moth.value, "Rosy Maple Moth")

        // Tooltip
        tb.add("item.estrogen.estrogen_pill.tooltip", "Temporary Source of _Girl Power_.")
        tb.add("item.estrogen.crystal_estrogen_pill.tooltip", "Temporary Source of _Girl Power_.")
        tb.add("item.estrogen.estrogen_patch.tooltip", "A refillable source of _Girl Power_.")
        tb.add("item.estrogen.estrogen_patch.tooltip.condition1", "When worn in the Thighs slot")
        tb.add(
            "item.estrogen.estrogen_patch.tooltip.behaviour1",
            "Gives the _Girl Power Effect_ for as long as it's worn and is filled with _Liquid Estrogen_."
        )
        tb.add("block.estrogen.dormant_dream_block.tooltip", "A block with some _very_ powerful properties...")
        tb.add(
            "item.estrogen.thigh_highs.tooltip",
            "A pair of Thigh Highs that give you _Fall Damage Resistance_. Non-special ones can be _dyed_ with 2 dyes on each side in a _crafting table_."
        )

        tb.add("block.estrogen.cookie_jar.tooltip", "A jar to _store_ all your _Cookies_!")
        tb.add("block.estrogen.dreamcatcher.tooltip", "Hang to ward off _bad_ dreams!")
        tb.add("block.estrogen.colon_three_block.tooltip", "_Why?_")

        // Advancements
        tb.add("advancement.estrogen.root.title", "Estrogen")
        tb.add(
            "advancement.estrogen.root.description",
            "A Mod Based around expressing yourself, fluid handling and discovery!"
        )
        tb.add("advancement.estrogen.horse_urine.title", "Ewww!")
        tb.add(
            "advancement.estrogen.horse_urine.description",
            "Collect Horse Urine by \"milking\" a horse with a bottle."
        )
        tb.add("advancement.estrogen.wet_sponge.title", "First Step in Processing")
        tb.add(
            "advancement.estrogen.wet_sponge.description",
            "Use a filter or a Sponge to remove the impurities from the Horse Urine you collected."
        )
        tb.add("advancement.estrogen.liquid_estrogen.title", "The Source of (Girl) Power")
        tb.add("advancement.estrogen.liquid_estrogen.description", "Collect a Bucket of Liquid Estrogen.")
        tb.add("advancement.estrogen.estrogen_pill.title", "Pill up, gals!")
        tb.add("advancement.estrogen.estrogen_pill.description", "Craft the elusive Estrogen Pill.")
        tb.add("advancement.estrogen.estrogen_patch.title", "(Almost) Permanent!")
        tb.add(
            "advancement.estrogen.estrogen_patch.description",
            "Craft the Estrogen Patch and enjoy your long lasting femininity."
        )
        tb.add("advancement.estrogen.colon_three.title", ":3")
        tb.add("advancement.estrogen.colon_three.description", ":3333333333")
        tb.add("advancement.estrogen.balls.title", "Hehe")
        tb.add("advancement.estrogen.balls.description", "What happens when you split the Slime and the Balls?")
        tb.add("advancement.estrogen.cookie_jar.title", "Accept & Continue")
        tb.add(
            "advancement.estrogen.cookie_jar.description",
            "This mod requires cookies to work properly. Please insert them into the jar"
        )
        tb.add("advancement.estrogen.hard_to_catch.title", "Fact: Birds are hard to catch")
        tb.add("advancement.estrogen.hard_to_catch.description", "Wow. It's fast!")
        tb.add("advancement.estrogen.estrogen_dealer.title", "Estrogen Dealer")
        tb.add("advancement.estrogen.estrogen_dealer.description", "I am the one who knocks")

        // Blocks
        tb.add(EstrogenBlocks.DreamCatcher.value!!, "Dreamcatcher")
        tb.add(EstrogenBlocks.ColonThreeBlock.value!!, "Block of :3")
        tb.add(EstrogenBlocks.CookieJar.value!!, "Cookie Jar")
        tb.add(EstrogenBlocks.DreamBlock.value!!, "Dream Block")
        tb.add(EstrogenBlocks.EstrogenPillBlock.value!!, "Estrogen Pill Box")
        tb.add(EstrogenBlocks.MothWool.value!!, "Rosy Maple Wool")
        tb.add(EstrogenBlocks.QuiltedMothWool.value!!, "Quilted Rosy Maple Wool")
        tb.add(EstrogenBlocks.MothCarpet.value!!, "Rosy Maple Wool Carpet")
        tb.add(EstrogenBlocks.QuiltedMothCarpet.value!!, "Quilted Rosy Maple Wool Carpet")
        tb.add(EstrogenBlocks.MothBed.value!!, "Rosy Maple Bed")
        tb.add(EstrogenBlocks.QuiltedMothBed.value!!, "Quilted Rosy Maple Bed")
        tb.add(EstrogenBlocks.Memorial.value!!, "Memorial")
        tb.add("block.estrogen.memorial.line1", "-- Estrogen Mod --")
        tb.add("block.estrogen.memorial.line2", "This memorial dedicated to those")
        tb.add("block.estrogen.memorial.line3", "Who perished on the climb")
        tb.add(EstrogenFluids.LiquidEstrogen.block, "Liquid Estrogen")
        tb.add(EstrogenFluids.HorseUrine.block, "Horse Urine")
        tb.add(EstrogenFluids.FiltratedHorseUrine.block, "Filtrated Horse Urine")
        tb.add(EstrogenFluids.MoltenSlime.block, "Molten Slime")
        tb.add(EstrogenFluids.MoltenAmethyst.block, "Molten Amethyst")
        tb.add(EstrogenFluids.TestosteroneMixture.block, "Testosterone Mixture")
        tb.add(EstrogenFluids.GenderFluid.block, "Gender Fluid")
        tb.add(EstrogenBlocks.LiquidEstrogenCauldron.value!!, "Liquid Estrogen Cauldron")
        tb.add(EstrogenBlocks.FiltratedHorseUrineCauldron.value!!, "Filtrated Horse Urine Cauldron")
        tb.add(EstrogenBlocks.HorseUrineCauldron.value!!, "Horse Urine Cauldron")

        // Fluids
        tb.add("fluid_type.estrogen.liquid_estrogen", "Liquid Estrogen")
        tb.add("fluid_type.estrogen.horse_urine", "Horse Urine")
        tb.add("fluid_type.estrogen.filtrated_horse_urine", "Filtrated Horse Urine")
        tb.add("fluid_type.estrogen.molten_slime", "Molten Slime")
        tb.add("fluid_type.estrogen.molten_amethyst", "Molten Amethyst")
        tb.add("fluid_type.estrogen.testosterone_mixture", "Testosterone Mixture")
        tb.add("fluid_type.estrogen.gender_fluid", "Gender Fluid")
        tb.add("fluid.estrogen.liquid_estrogen", "Liquid Estrogen")
        tb.add("fluid.estrogen.horse_urine", "Horse Urine")
        tb.add("fluid.estrogen.filtrated_horse_urine", "Filtrated Horse Urine")
        tb.add("fluid.estrogen.molten_slime", "Molten Slime")
        tb.add("fluid.estrogen.molten_amethyst", "Molten Amethyst")
        tb.add("fluid.estrogen.testosterone_mixture", "Testosterone Mixture")
        tb.add("fluid.estrogen.gender_fluid", "Gender Fluid")

        // Sounds
        tb.add("subtitles.estrogen.dash", "Girl Power Dash")
        tb.add("subtitles.estrogen.music.g03c", "erora - G03C")
        tb.add("subtitles.estrogen.music.trust_yourself", "erora - Trust Yourself")
        tb.add("subtitles.estrogen.music.amphitrite", "erora - Amphitrite")
        tb.add("subtitles.estrogen.music.aurum_berry", "erora - Aurum Berry")
        tb.add("subtitles.estrogen.music.inferred_dreams", "erora - Inferred Dreams")
        tb.add("subtitles.estrogen.music.sleeping_dreams", "erora - Sleeping Dreams")
        tb.add("subtitles.estrogen.music.inner_self_realization", "erora - Inner Self-realization")
        tb.add("subtitles.estrogen.block.cookie_jar.full", "Cookie Jar is full")
        tb.add("subtitles.estrogen.block.cookie_jar.insert", "Cookie Jar gets inserted into")
        tb.add("subtitles.estrogen.entity.moth.death", "Rosy Maple Moth dies")
        tb.add("subtitles.estrogen.entity.moth.hurt", "Rosy Maple Moth hurts")
        tb.add("subtitles.estrogen.entity.moth.loop", "Rosy Maple Moth buzzes")
        tb.add("subtitles.estrogen.entity.moth.fuzz_up", "Rosy Maple Moth fuzzes up")
        tb.add("subtitles.estrogen.dream_block.enter", "Dream Block entered")
        tb.add("subtitles.estrogen.dream_block.exit", "Dream Block exited")

        // Jukebox Songs
        tb.add("jukebox_song.estrogen.g03c", "erora - G03C")

        // Death
        tb.add("death.attack.girlpower", "%s Girlbossed too hard")
        tb.add("death.attack.girlpower.player", "%s Girlbossed too hard")

        tb.add("death.attack.colon_three", "%s is not worthy.")
        tb.add("death.attack.colon_three.player", "%s is not worthy.")

        // Trinkets
        tb.add("trinkets.slot.legs.thighs", "Thighs")
        // Curios
        tb.add("curios.identifier.thighs", "Thighs")

        // Recipe Viewers
        tb.add("estrogen.recipe.centrifuging", "Centrifuging")
        tb.add("estrogen.recipe.entity_interaction", "Entity Interaction")
        tb.add("estrogen.recipe.liquid_estrogen_cauldron", "Cauldron Shaking")
        tb.add("estrogen.recipe.liquid_estrogen_cauldron.tooltip", "Repeat")
        tb.add("estrogen.recipe.cauldron_interaction", "Cauldron Interaction")
        tb.add("estrogen.recipe.sponging", "Sponging")

        tb.add("estrogen.recipe.common.disabled", "A Module disables this Recipe")

        // Enchantments
        tb.add("enchantment.estrogen.uwufy_curse", "Curse of Uwufying")
        tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUfies your chat messages >///<")

        // EMI
        tb.add("emi.category.estrogen.liquid_estrogen_cauldron", "Cauldron Shaking")
        tb.add("emi.category.estrogen.entity_interaction", "Entity Interaction")
        tb.add("emi.category.estrogen.sponging", "Sponging")
        tb.add("emi.category.estrogen.cauldron_interaction", "Cauldron Interaction")

        // Tags
        // Items
        tb.add("tag.item.trinkets.legs.thighs", "Thighs")
        tb.add("tag.item.estrogen.uwufying", "Uwufying")
        tb.add("tag.item.curios.thighs", "Thighs")
        tb.add("tag.item.c.cookies", "Cookies")
        tb.add("tag.item.c.leather_items", "Leather Items")
        tb.add("tag.item.c.light_emitters", "Light Emitters")
        tb.add("tag.item.estrogen.chest_armor_ignore", "Chest Armor Ignore")
        tb.add("tag.item.estrogen.enchantable.head", "Enchantable Head Armor")
        // Fluids
        tb.add("tag.fluid.estrogen.urine", "Urine")
        tb.add("tag.fluid.estrogen.sponge_ignoring", "Sponge Ignoring")

        // Ponder
        // Centrifuge
        tb.add("estrogen.ponder.intro.header", "The Centrifuge Requirements")
        tb.add("estrogen.ponder.intro.text_1", "The Centrifuge needs the maximum speed (256 RPM) to work!")
        tb.add("estrogen.ponder.basic.header", "How to use the Centrifuge")
        tb.add(
            "estrogen.ponder.basic.text_1",
            "The Centrifuge doesn't have any inventory, you will need to place fluid containers around it to make it work!"
        )
        tb.add("estrogen.ponder.basic.text_2", "You can input fluids from the bottom")
        tb.add("estrogen.ponder.basic.text_3", "And output fluids from the top")

        // Attributes
        tb.add(EstrogenAttributes.DashLevel.holder, "Dash Level")
        tb.add(EstrogenAttributes.BoobGrowingStartTime.holder, "Upper Body Start Time")
        tb.add(EstrogenAttributes.BoobInitialSize.holder, "Upper Body initial size")
        tb.add(EstrogenAttributes.FallDamageResistance.holder, "Fall Damage Resistance")
        tb.add(EstrogenAttributes.ShowBoobs.holder, "Upper Body Visibility")

        // Display Sources
        tb.add("estrogen.display_source.entity_name", "Entity Name")

        // Mod Menu
        tb.add("estrogen.credits", "Credits & Contributors")

        // Menu Screen
        tb.add("estrogen.screen.menu.title", "Estrogen Menu")
        tb.add("estrogen.button.config", "Config")
        tb.add("estrogen.button.module_configs", "Modules")
        tb.add("estrogen.button.cosmetics","Cosmetics")
        tb.add("estrogen.button.memorial", "Memorial")
            tb.add("estrogen.button.memorial.desc", "Unlock the memorial by finding it in-game.")
        tb.add("estrogen.button.close", "Close")

        // Curios :eyeroll:
        tb.add("curios.modifiers.thighs", "Thigh High Modifiers")

        // Thigh high tooltips - Cryptic asf because * colors * :)
        thighHigh(tb, "trans", "§bTr§dan§fsge§dnd§ber")
        thighHigh(tb, "pansexual", "§dPan§esex§bual")
        thighHigh(tb, "polysexual", "§dPoly§asex§bual")
        thighHigh(tb, "lesbian", "§cL§6es§fb§dia§5n")
        thighHigh(tb, "pride", "§cP§6r§ei§ad§5e")
        thighHigh(tb, "bisexual", "§dBis§5ex§1ual")
        thighHigh(tb, "asexual", "§5As§fex§8ual")
        thighHigh(tb, "aromantic", "§aAro§fman§8tic")
        thighHigh(tb, "genderqueer", "§aGen§fder§5queer")
        thighHigh(tb, "non_binary", "§eNon§f-Bi§5na§8ry")
        thighHigh(tb, "intersex", "§eInt§5er§esex")
        thighHigh(tb, "polyamorous", "§bPoly§fam§coro§8us")
        thighHigh(tb, "agender", "§8A§7g§fe§an§fd§7e§8r")
        thighHigh(tb, "vincian", "§3Vi§an§fc§bia§9n")
        thighHigh(tb, "genderfluid", "§dGe§fnd§5er§8fl§9uid")
        thighHigh(tb, "demisexual", "De§8mi§fse§5xu§fal")

        // Cosmetics
        tb.add("gui.estrogen.cosmetics.title", "Estrogen Cosmetics")
        tb.add("gui.estrogen.cosmetics.back", "Go Back")
        tb.add("gui.estrogen.cosmetics.close", "Close")
        tb.add("gui.estrogen.cosmetics.login", "Login")
        tb.add("gui.estrogen.cosmetics.claim", "Claim Reward")
        tb.add("gui.estrogen.cosmetics.none", "None")
        tb.add("gui.estrogen.cosmetics.no_preview", "Cosmetics Preview only in-game")
        tb.add("gui.estrogen.cosmetics.no_cosmetics", "No Cosmetics Available")
        tb.add("gui.estrogen.cosmetics.patreon_ad", "Get Cosmetics on Patreon")

        tb.add(
            "gui.estrogen.cosmetics.login.description", """
            Welcome to Estrogen Cosmetics!
            
            To get started, please log in to your account.
            
            """.trimIndent()
        )
        tb.add("gui.estrogen.cosmetics.login.button", "Login")
        tb.add("gui.estrogen.cosmetics.login.init", "Logging in...")
        tb.add("gui.estrogen.cosmetics.login.unauthorized", "Failed to login, check if you are logged in to Minecraft.")
        tb.add("gui.estrogen.cosmetics.login.server_error", "Error occurred on server side while logging in.")
        tb.add("gui.estrogen.cosmetics.login.failed", "Unknown error occurred while logging in.")

        tb.add("gui.estrogen.cosmetics.init", "Getting Cosmetics...")
        tb.add("gui.estrogen.cosmetics.unauthorized", "Failed to get cosmetics, try again later.")
        tb.add("gui.estrogen.cosmetics.server_error", "Error occurred on server side while getting cosmetics.")
        tb.add("gui.estrogen.cosmetics.failed", "Unknown error occurred while getting cosmetics.")

        tb.add(
            "gui.estrogen.cosmetics.claim.description", """
            Welcome to Estrogen Cosmetics Reward Claim!
            
            Enter the code you received,
            into the text box below, to claim your cosmetic.
            
            """.trimIndent()
        )
        tb.add("gui.estrogen.cosmetics.claim.button", "Claim")
        tb.add("gui.estrogen.cosmetics.claim.init", "Claiming...")
        tb.add("gui.estrogen.cosmetics.claim.forbidden", "Failed, code was already claimed.")
        tb.add("gui.estrogen.cosmetics.claim.not_found", "Failed, code is invalid.")
        tb.add("gui.estrogen.cosmetics.claim.failed", "Unknown error occurred while claiming.")

        // Datapacks
        tb.add("datapacks.estrogen.vanillamode", "§eVanilla§7: §3Estrogen")
        tb.add("datapack.estrogen.vanillamode", "§lExtra§r Unbalanced Recipes for Estrogen mod")

        // Generic
        tb.add("estrogen.generic.coming_soon", "Coming Soon")
    }

    private fun thighHigh(builder: TranslationBuilder, styleName: String?, value: String?) {
        builder.add("tooltip.thigh_highs.estrogen." + styleName, value)
    }
}