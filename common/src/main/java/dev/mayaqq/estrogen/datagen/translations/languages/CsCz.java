package dev.mayaqq.estrogen.datagen.translations.languages;

import dev.mayaqq.estrogen.registry.common.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class CsCz extends FabricLanguageProvider {

    public CsCz(FabricDataOutput output) {
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
        tb.add("itemGroup.estrogen", "Estrogen");
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
