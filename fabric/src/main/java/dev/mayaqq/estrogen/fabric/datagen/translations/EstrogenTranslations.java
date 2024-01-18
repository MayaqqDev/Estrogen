package dev.mayaqq.estrogen.fabric.datagen.translations;

import dev.mayaqq.estrogen.registry.common.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EstrogenTranslations {
    public static class EnUs extends FabricLanguageProvider {

        public EnUs(FabricDataOutput output) {
            super(output, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            // Config
                tb.add("text.autoconfig.estrogen.title", "Estrogen");
                tb.add("text.autoconfig.estrogen.option.boobies", "Chest Feature");
            // Status Effects
                tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Girl Power");

            // Controls
            tb.add("category.estrogen", "Estrogen");
                tb.add("key.estrogen.dash", "Activate Dash");

            // Items
            tb.add("itemGroup.estrogen", "Estrogen");
                tb.add(EstrogenItems.ESTROGEN_PILL.get(), "Estrogen Pill");
                tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Estrogen Patch");
                tb.add("item.estrogen.estrogen_patches_plural", "Estrogen Patches");
                tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Incomplete Estrogen Patch");
                tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), "Crystal Estrogen Pill");
                tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get(), "Estrogen Chip Cookie");
                    tb.add("item.estrogen.estrogen_chip_cookie.desc", "Erora - G03C");
                tb.add(EstrogenItems.BALLS.get(), "Balls");
                tb.add(EstrogenItems.HORSE_URINE_BOTTLE.get(), "Horse Urine Bottle");
                tb.add(EstrogenItems.TESTOSTERONE_CHUNK.get(), "Testosterone Chunk");
                tb.add(EstrogenItems.TESTOSTERONE_POWDER.get(), "Testosterone Powder");
                tb.add(EstrogenItems.USED_FILTER.get(), "Used Filter");
                tb.add(EstrogenItems.UWU.get(), ":3");
                    tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
                tb.add(EstrogenItems.INCOMPLETE_UWU.get(), "Incomplete UwU");
                // Buckets
                tb.add(EstrogenFluidItems.LIQUID_ESTROGEN_BUCKET.get(), "Bucket of Liquid Estrogen");
                tb.add(EstrogenFluidItems.HORSE_URINE_BUCKET.get(), "Bucket of Horse Urine");
                tb.add(EstrogenFluidItems.FILTRATED_HORSE_URINE_BUCKET.get(), "Bucket of Filtrated Horse Urine");
                tb.add(EstrogenFluidItems.MOLTEN_SLIME_BUCKET.get(), "Bucket of Molten Slime");
                tb.add(EstrogenFluidItems.MOLTEN_AMETHYST_BUCKET.get(), "Bucket of Molten Amethyst");
                tb.add(EstrogenFluidItems.TESTOSTERONE_MIXTURE_BUCKET.get(), "Bucket of Testosterone Mixture");

            // Blocks
                tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrifuge");
                // Fluids
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

            // Trinkets
                tb.add("trinkets.slot.legs.thighs", "Thighs");
            // Curios
                tb.add("curios.identifier.thighs", "Thighs");

            // REI
                tb.add("create.recipe.centrifuging", "Centrifuging");

            // Enchantments
                tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "Curse of Uwufying");

            // EMI
                tb.add("emi.category.estrogen.centrifuging", "Centrifuging");

            // Tags
                // Items
                tb.add("tag.item.trinkets.legs.thighs", "Thighs");
                tb.add("tag.item.estrogen.uwufying", "Uwufying");
                tb.add("tag.item.curios.thighs", "Thighs");
                tb.add("tag.item.estrogen.copper_plates", "Copper Plates");

            // Ponder
                // Centrifuge
                tb.add("estrogen.ponder.intro.header", "The Centrifuge Requirements");
                    tb.add("estrogen.ponder.intro.text_1", "The centrifuge needs the maximum speed (256 RPM) to work!");
                tb.add("estrogen.ponder.basic.header", "How to use the Centrifuge");
                    tb.add("estrogen.ponder.basic.text_1", "The Centrifuge doesn't have any inventory, you will need to place fluid containers around it to make it work!");
                    tb.add("estrogen.ponder.basic.text_2", "You can input fluids from the bottom");
                    tb.add("estrogen.ponder.basic.text_3", "And output fluids from the top");

            // Attributes
                tb.add("attribute.name.estrogen.dash_level", "Dash Level");
                tb.add("attribute.name.estrogen.boob_growing_start_time", "Upper body Start Time");
                tb.add("attribute.name.estrogen.boob_initial_size", "Upper Body initial size");
        }
    }

    public static class FrFr extends FabricLanguageProvider {

        public FrFr(FabricDataOutput output) {
            super(output, "fr_fr");
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            //TODO: French translations fix
            /*
            // Config
                tb.add("text.autoconfig.estrogen.title", "Estrogen");
                tb.add("text.autoconfig.estrogen.option.boobies", "Chest Feature");
            // Status Effects
                tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Girl Power");

            // Controls
                tb.add("category.estrogen", "Oestrogènes");
                tb.add("key.estrogen.dash", "Activer/Désactiver Dash");

            // Items
                tb.add("itemGroup.estrogen", "Oestrogène");
                tb.add(EstrogenItems.ESTROGEN_PILL, "Pillule d'Oestrogènes");
                tb.add(EstrogenItems.ESTROGEN_PATCHES, "Patchs d'oestrogènes");
                tb.add("item.estrogen.estrogen_patches_plural", "Patch d'oestrogènes");
                tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH, "Patch d'Oestrogènes Incomplet");
                tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL, "Pillule d'Oestrogènes de Cristal");
                tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE, "Biscuit aux oestrogènes");
                tb.add(EstrogenItems.BALLS, "Boules");
                tb.add(EstrogenItems.HORSE_URINE_BOTTLE, "Bouteille d'Urine de Cheval");
                tb.add(EstrogenItems.TESTOSTERONE_CHUNK, "Morceau de Testostérone");
                tb.add(EstrogenItems.TESTOSTERONE_POWDER, "Poudre de Testostérone");
                tb.add(EstrogenItems.USED_FILTER, "Filtre Utilisé");
                tb.add(EstrogenItems.UWU, ":3");
                    tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
                tb.add(EstrogenItems.INCOMPLETE_UWU, "UwU Incomplet");
                // Buckets
                tb.add(EstrogenFluids.LIQUID_ESTROGEN.bucket(), "Sceau d'Oestrogènes Liquide");
                tb.add(EstrogenFluids.HORSE_URINE.bucket(), "Sceau d'Urine de Cheval");
                tb.add(EstrogenFluids.FILTRATED_HORSE_URINE.bucket(), "Sceau d'Urine de Cheval Filtrée");
                tb.add(EstrogenFluids.MOLTEN_SLIME.bucket(), "Sceau de Slime Fondu");
                tb.add(EstrogenFluids.MOLTEN_AMETHYST.bucket(), "Sceau d'Améthyste Fondu");
                tb.add(EstrogenFluids.TESTOSTERONE_MIXTURE.bucket(), "Sceau de Mixture de Testostérone");

            // Blocks
                tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrifugeuse");
                tb.add(EstrogenFluids.LIQUID_ESTROGEN_BLOCK, "Oestrogène Liquide");
                tb.add(EstrogenFluids.HORSE_URINE_BLOCK, "Urine de Cheval");
                tb.add(EstrogenFluids.FILTRATED_HORSE_URINE_BLOCK, "Urine de Cheval Filtrée");
                tb.add(EstrogenFluids.MOLTEN_SLIME_BLOCK, "Slime Fondu");
                tb.add(EstrogenFluids.MOLTEN_AMETHYST_BLOCK, "Améthyste Fondu");
                tb.add(EstrogenFluids.TESTOSTERONE_MIXTURE_BLOCK, "Mixture de Testosterone");

            // Sounds
                tb.add("subtitles.estrogen.dash", "Girl Power Dash");

            // Death
                tb.add("death.attack.girlpower", "%s a Girlboss trop fort");

            // Trinkets
                tb.add("trinkets.slot.legs.thighs", "Cuisses");

            // REI
                tb.add("create.recipe.centrifuging", "Centrifugation");

            // Enchantments
                tb.add(EstrogenEnchantments.UWUFYING_CURSE, "Curse of Uwufying");
                    tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUfies yowr Chat Messages >w<");

            // EMI
                tb.add("emi.category.estrogen.centrifuging", "Centrifugation");

            // Tags
                // Item
                tb.add("tag.item.trinkets.legs.thighs", "Cuisses");
                tb.add("tag.item.estrogen.uwufying", "Uwufying");

             */
        }
    }

    public static class HuHu extends FabricLanguageProvider {

        public HuHu(FabricDataOutput output) {
            super(output, "hu_hu");
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            // Config
            tb.add("text.autoconfig.estrogen.title", "Ösztrogén");
            tb.add("text.autoconfig.estrogen.option.boobies", "Mell");
            // Status Effects
            tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Lány Erő");

            // Controls
            tb.add("category.estrogen", "Ösztrogén");
            tb.add("key.estrogen.dash", "Dash Aktiválása");

            // Items
            tb.add("itemGroup.estrogen", "Ösztrogén");
            tb.add(EstrogenItems.ESTROGEN_PILL.get(), "Ösztrogén tabletta");
            tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Ösztrogén tapasz");
            tb.add("item.estrogen.estrogen_patches_plural", "Ösztrogén tapaszok");
            tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Befejezetlen ösztrogén tapasz");
            tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), "Kristály ösztrogén tabletta");
            tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get(), "Ösztrogénes süti");
            tb.add(EstrogenItems.BALLS.get(), "Golyók");
            tb.add(EstrogenItems.HORSE_URINE_BOTTLE.get(), "Lóvizeletes palack");
            tb.add(EstrogenItems.TESTOSTERONE_CHUNK.get(), "Tesztoszteron darab");
            tb.add(EstrogenItems.TESTOSTERONE_POWDER.get(), "Tesztoszteron por");
            tb.add(EstrogenItems.USED_FILTER.get(), "Használt szűrő");
            tb.add(EstrogenItems.UWU.get(), ":3");
            tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
            tb.add(EstrogenItems.INCOMPLETE_UWU.get(), "Befejezetlen UwU");
            // Buckets
            tb.add(EstrogenFluidItems.LIQUID_ESTROGEN_BUCKET.get(), "Folyékony ösztrogénes vödör");
            tb.add(EstrogenFluidItems.HORSE_URINE_BUCKET.get(), "Lóvizeletes vödör");
            tb.add(EstrogenFluidItems.FILTRATED_HORSE_URINE_BUCKET.get(), "Szűrt lóvizeletes vödör");
            tb.add(EstrogenFluidItems.MOLTEN_SLIME_BUCKET.get(), "Olvasztott nyálkás vödör");
            tb.add(EstrogenFluidItems.MOLTEN_AMETHYST_BUCKET.get(), "Olvasztott ametisztes vödör");
            tb.add(EstrogenFluidItems.TESTOSTERONE_MIXTURE_BUCKET.get(), "Tesztoszteron keverékes vödör");

            // Blocks
            tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrifuga");
            // Fluids
            tb.add("fluid.estrogen.liquid_estrogen", "Folyékony ösztrogén");
            tb.add("fluid.estrogen.horse_urine", "Lóvizelet");
            tb.add("fluid.estrogen.filtrated_horse_urine", "Szűrt lóvizelet");
            tb.add("fluid.estrogen.molten_slime", "Olvasztott nyálka");
            tb.add("fluid.estrogen.molten_amethyst", "Olvasztott ametiszt");
            tb.add("fluid.estrogen.testosterone_mixture", "Tesztoszteron keverék");

            // Sounds
            tb.add("subtitles.estrogen.dash", "Lány Erő Dash");

            // Death
            tb.add("death.attack.girlpower", "%s túl erős Girlboss");

            // Trinkets
            tb.add("trinkets.slot.legs.thighs", "Combok");
            // Curios
            tb.add("curios.identifier.thighs", "Combok");

            // REI
            tb.add("create.recipe.centrifuging", "Centrifugálás");

            // Enchantments
            tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "UwUsítás átka");

            // EMI
            tb.add("emi.category.estrogen.centrifuging", "Centrifugálás");

            // Tags
            // Items
            tb.add("tag.item.trinkets.legs.thighs", "Combok");
            tb.add("tag.item.estrogen.uwufying", "UwUsítás");
            tb.add("tag.item.curios.thighs", "Combok");

            // Ponder
            // Centrifuge
            tb.add("estrogen.ponder.intro.header", "Centrifuga követelmények");
            tb.add("estrogen.ponder.intro.text_1", "A centrifugának maximális sebesség (256 RPM) kell, hogy működjön!");
            tb.add("estrogen.ponder.basic.header", "Hogyan használd a centrifugát");
            tb.add("estrogen.ponder.basic.text_1", "A centrifugának nincs eszköztára, folyadék tárolókat kell köré rakni!");
            tb.add("estrogen.ponder.basic.text_2", "Alulról tudsz folyadékokat betölteni");
            tb.add("estrogen.ponder.basic.text_3", "Felül van a kimenet");

            // Attributes
            tb.add("attribute.name.estrogen.dash_level", "Dash szint");
            tb.add("attribute.name.estrogen.boob_growing_start_time", "Felsőtest kezdő idő");
            tb.add("attribute.name.estrogen.boob_initial_size", "Felsőtest kezdeti méret");
        }
    }
}