package dev.mayaqq.estrogen.datagen.translations.languages;

import dev.mayaqq.estrogen.registry.common.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class HuHu extends FabricLanguageProvider {

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
