package dev.mayaqq.estrogen.datagen.translations;

import dev.mayaqq.estrogen.registry.common.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EstrogenTranslations {
    public static class EnUs extends FabricLanguageProvider {

        public EnUs(FabricDataGenerator output) {
            super(output, "en_us");
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            // Status Effects
                tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Girl Power");

            // Controls
            tb.add("category.estrogen", "Estrogen");
                tb.add("key.estrogen.dash", "Activate Dash");

            // Items
            tb.add("itemGroup.estrogen.estrogen", "Estrogen");
                tb.add(EstrogenItems.ESTROGEN_PILL.get(), "Estrogen Pill");
                tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Estrogen Patch");
                tb.add("item.estrogen.estrogen_patches_plural", "Estrogen Patches");
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
                tb.add("death.attack.girlpower.player", "%s Girlbossed too hard");

            // Trinkets
                tb.add("trinkets.slot.legs.thighs", "Thighs");
            // Curios
                tb.add("curios.identifier.thighs", "Thighs");

            // REI
                tb.add("create.recipe.centrifuging", "Centrifuging");

            // Enchantments
                tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "Curse of Uwufying");
                tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUfies your chat messages >///<");

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

    public static class CsCz extends FabricLanguageProvider {

        public CsCz(FabricDataGenerator output) {
            super(output, "cs_cz");
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            // Status Effects
            tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Holčičí síla");

            // Controls
            tb.add("category.estrogen", "Estrogen");
            tb.add("key.estrogen.dash", "Aktivovat Dash");

            // Items
            tb.add("itemGroup.estrogen.estrogen", "Estrogen");
            tb.add(EstrogenItems.ESTROGEN_PILL.get(), "Pilulka Estrogenu");
            tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Estrogenová Náplast");
            tb.add("item.estrogen.estrogen_patches_plural", "Estrogenové Náplasti");
            tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Nedokončená Estrogenová Náplast");
            tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), "Krystalová pilulka estrogenu");
            tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get(), "Estrogenová Sušenka");
            tb.add("item.estrogen.estrogen_chip_cookie.desc", "erora - G03C");
            tb.add(EstrogenItems.BALLS.get(), "Koule");
            tb.add(EstrogenItems.HORSE_URINE_BOTTLE.get(), "Láhevička S Koňskou Močí");
            tb.add(EstrogenItems.TESTOSTERONE_CHUNK.get(), "Kus Testosteronu");
            tb.add(EstrogenItems.TESTOSTERONE_POWDER.get(), "Testosteronový Prášek");
            tb.add(EstrogenItems.USED_FILTER.get(), "Použitý Filtr");
            tb.add(EstrogenItems.UWU.get(), ":3");
            tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
            tb.add(EstrogenItems.INCOMPLETE_UWU.get(), "Nedokončené UwU");
            // Buckets
            tb.add(EstrogenFluidItems.LIQUID_ESTROGEN_BUCKET.get(), "Kbelík Tekutého Estrogenu");
            tb.add(EstrogenFluidItems.HORSE_URINE_BUCKET.get(), "Kbelík Koňské Moči");
            tb.add(EstrogenFluidItems.FILTRATED_HORSE_URINE_BUCKET.get(), "Kbelík Filtrované Koňské Moči");
            tb.add(EstrogenFluidItems.MOLTEN_SLIME_BUCKET.get(), "Kbelík Rozpuštěného Slizu");
            tb.add(EstrogenFluidItems.MOLTEN_AMETHYST_BUCKET.get(), "Kbelík Rozpuštěného Ametystu");
            tb.add(EstrogenFluidItems.TESTOSTERONE_MIXTURE_BUCKET.get(), "Kbelík Testosteronové Směsi");

            // Blocks
            tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrifuga");
            // Fluids
            tb.add("fluid.estrogen.liquid_estrogen", "Tekutý Estrogen");
            tb.add("fluid.estrogen.horse_urine", "Koňská Moč");
            tb.add("fluid.estrogen.filtrated_horse_urine", "Filtrovaná Koňská Moč");
            tb.add("fluid.estrogen.molten_slime", "Rozpuštěný Sliz");
            tb.add("fluid.estrogen.molten_amethyst", "Rozpuštěný Ametyst");
            tb.add("fluid.estrogen.testosterone_mixture", "Testosteronová Směs");

            // Sounds
            tb.add("subtitles.estrogen.dash", "Dash Holčičí Síly");

            // Death
            tb.add("death.attack.girlpower", "hráč %s Girlbossoval příliš tvrdě");
            tb.add("death.attack.girlpower.player", "hráč %s Girlbossoval příliš tvrdě");

            // Trinkets
            tb.add("trinkets.slot.legs.thighs", "Stehna");
            // Curios
            tb.add("curios.identifier.thighs", "Stehna");

            // REI
            tb.add("create.recipe.centrifuging", "Centrifugování");

            // Enchantments
            tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "Prokletí UwUfikování");
            tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUfikuje tvé chat zprávy >///<");

            // EMI
            tb.add("emi.category.estrogen.centrifuging", "Centrifugování");

            // Tags
            // Items
            tb.add("tag.item.trinkets.legs.thighs", "Stehna");
            tb.add("tag.item.estrogen.uwufying", "UwUfikování");
            tb.add("tag.item.curios.thighs", "Stehna");
            tb.add("tag.item.estrogen.copper_plates", "Měděné Pláty");

            // Ponder
            // Centrifuge
            tb.add("estrogen.ponder.intro.header", "Požadavky Centrifugy");
            tb.add("estrogen.ponder.intro.text_1", "Centrifuga potřebuje maximální rychlost (256 RPM) aby fungovala!");
            tb.add("estrogen.ponder.basic.header", "Jak používat Centrifugu");
            tb.add("estrogen.ponder.basic.text_1", "Centrifuga nemá žádný inventář, musíš kolem ní umístit nádoby na tekutiny aby fungovala!");
            tb.add("estrogen.ponder.basic.text_2", "Můžeš do ní vkládat tekutiny zespodu");
            tb.add("estrogen.ponder.basic.text_3", "A vypouštět tekutiny zeshora");

            // Attributes
            tb.add("attribute.name.estrogen.dash_level", "Úroveň Dashe");
            tb.add("attribute.name.estrogen.boob_growing_start_time", "Čas Začátku Růstu Horní Poloviny Těla");
            tb.add("attribute.name.estrogen.boob_initial_size", "Počáteční Velikost Horní Poloviny Těla");
        }
    }

    public static class HuHu extends FabricLanguageProvider {

        public HuHu(FabricDataGenerator output) {
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
            tb.add("itemGroup.estrogen.estrogen", "Ösztrogén");
            tb.add(EstrogenItems.ESTROGEN_PILL.get(), "Ösztrogén tabletta");
            tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Ösztrogén tapasz");
            tb.add("item.estrogen.estrogen_patches_plural", "Ösztrogén tapaszok");
            tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Befejezetlen ösztrogén tapasz");
            tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), "Kristály ösztrogén tabletta");
            tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get(), "Ösztrogénes süti");
            tb.add("item.estrogen.estrogen_chip_cookie.desc", "erora - G03C");
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
            tb.add("death.attack.girlpower.player", "%s túl erős Girlboss");

            // Trinkets
            tb.add("trinkets.slot.legs.thighs", "Combok");
            // Curios
            tb.add("curios.identifier.thighs", "Combok");

            // REI
            tb.add("create.recipe.centrifuging", "Centrifugálás");

            // Enchantments
            tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "UwUsítás átka");
            tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUsítja a chat üzeneteidet >///<");

            // EMI
            tb.add("emi.category.estrogen.centrifuging", "Centrifugálás");

            // Tags
            // Items
            tb.add("tag.item.trinkets.legs.thighs", "Combok");
            tb.add("tag.item.estrogen.uwufying", "UwUsítás");
            tb.add("tag.item.curios.thighs", "Combok");
            tb.add("tag.item.estrogen.copper_plates", "Rézlapok");

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

    public static class PtBr extends FabricLanguageProvider {

        public PtBr(FabricDataGenerator output) {
            super(output, "pt_br");
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            // Status Effects
                tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Poder Feminino");

            // Controls
            tb.add("category.estrogen", "Estrogênio");
                tb.add("key.estrogen.dash", "Ativar Dash");

            // Items
            tb.add("itemGroup.estrogen.estrogen", "Estrogênio");
                tb.add(EstrogenItems.ESTROGEN_PILL.get(), "Comprimido de Estrogênio");
                tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Adesivo de Estrogênio");
                tb.add("item.estrogen.estrogen_patches_plural", "Adesivos de Estrogênio");
                tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Adesivo Incompleto de Estrogênio");
                tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), "Pilula de Estrogênio Cristalizado");
                tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get(), "Cookie de Estrogênio");
                    tb.add("item.estrogen.estrogen_chip_cookie.desc", "erora - G03C");
                tb.add(EstrogenItems.BALLS.get(), "Bolas");
                tb.add(EstrogenItems.HORSE_URINE_BOTTLE.get(), "Garrafa de Urina de Cavalo");
                tb.add(EstrogenItems.TESTOSTERONE_CHUNK.get(), "Pedaço de Testosterona");
                tb.add(EstrogenItems.TESTOSTERONE_POWDER.get(), "Pó de Testosterona");
                tb.add(EstrogenItems.USED_FILTER.get(), "Filtro Usado");
                tb.add(EstrogenItems.UWU.get(), ":3");
                    tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
                tb.add(EstrogenItems.INCOMPLETE_UWU.get(), "UwU Incompleto");
                // Buckets
                tb.add(EstrogenFluidItems.LIQUID_ESTROGEN_BUCKET.get(), "Balde de Estrogênio Liquido");
                tb.add(EstrogenFluidItems.HORSE_URINE_BUCKET.get(), "Balde de Urina de Cavalo");
                tb.add(EstrogenFluidItems.FILTRATED_HORSE_URINE_BUCKET.get(), "Balde de Urina de Cavalo Filtrada");
                tb.add(EstrogenFluidItems.MOLTEN_SLIME_BUCKET.get(), "Balde de Slime Derretido");
                tb.add(EstrogenFluidItems.MOLTEN_AMETHYST_BUCKET.get(), "Balde de Ametista Derretida");
                tb.add(EstrogenFluidItems.TESTOSTERONE_MIXTURE_BUCKET.get(), "Balde de Mistura de Testosterona");

            // Blocks
                tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrífuga");
                // Fluids
                tb.add("fluid.estrogen.liquid_estrogen", "Estrogênio Líquido");
                tb.add("fluid.estrogen.horse_urine", "Urina de Cavalo");
                tb.add("fluid.estrogen.filtrated_horse_urine", "Urina de Cavalo Filtrada");
                tb.add("fluid.estrogen.molten_slime", "Slime Derretido");
                tb.add("fluid.estrogen.molten_amethyst", "Ametista Derretida");
                tb.add("fluid.estrogen.testosterone_mixture", "Mistura de Testosterona");

            // Sounds
                tb.add("subtitles.estrogen.dash", "Dash de Poder Feminino");

            // Death
                tb.add("death.attack.girlpower", "%s foi chefona demais");
                tb.add("death.attack.girlpower.player", "%s foi chefona demais");

            // Trinkets
                tb.add("trinkets.slot.legs.thighs", "Coxas");
            // Curios
                tb.add("curios.identifier.thighs", "Coxas");

            // REI
                tb.add("create.recipe.centrifuging", "Centrifugando");

            // Enchantments
                tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "Maldição de Uwuficação");
                tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUifica suas mensagens do chat >///<");

            // EMI
                tb.add("emi.category.estrogen.centrifuging", "Centrifugando");

            // Tags
                // Items
                tb.add("tag.item.trinkets.legs.thighs", "Coxas");
                tb.add("tag.item.estrogen.uwufying", "Uwuficação");
                tb.add("tag.item.curios.thighs", "Coxas");
                tb.add("tag.item.estrogen.copper_plates", "Placas de Cobre");

            // Ponder
                // Centrifuge
                tb.add("estrogen.ponder.intro.header", "Requisitos da centrífuga");
                    tb.add("estrogen.ponder.intro.text_1", "A centrífuga precisa da rotação máxima (256RPM) pra funcionar!");
                tb.add("estrogen.ponder.basic.header", "Como usar a centrífuga");
                    tb.add("estrogen.ponder.basic.text_1", "A centrífuga não tem um inventário, você precisa por os recipientes de líquidos em volta");
                    tb.add("estrogen.ponder.basic.text_2", "Você pode inserir líquidos pela parte inferior");
                    tb.add("estrogen.ponder.basic.text_3", "E expelir líquidos pela parte superior!");

            // Attributes
                tb.add("attribute.name.estrogen.dash_level", "Nível do Dash");
                tb.add("attribute.name.estrogen.boob_growing_start_time", "Tempo de início das partes corporais superiores");
                tb.add("attribute.name.estrogen.boob_initial_size", "Tamanho inicial das partes corporais superiores");
        }
    }

}