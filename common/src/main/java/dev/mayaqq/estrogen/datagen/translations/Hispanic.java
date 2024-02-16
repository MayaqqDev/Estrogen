package dev.mayaqq.estrogen.datagen.translations;

import dev.mayaqq.estrogen.registry.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class Hispanic {
    public static void addAll(FabricDataGenerator.Pack pack) {
        pack.addProvider(EsAr::new);
        pack.addProvider(EsCl::new);
        pack.addProvider(EsEs::new);
        pack.addProvider(EsMx::new);
        pack.addProvider(EsUy::new);
        pack.addProvider(EsVe::new);
    }

    public static class EsAr extends SpanishLanguageProvider {
        public EsAr(FabricDataOutput output) {
            super(output, "es_ar");
        }
    }

    public static class EsCl extends SpanishLanguageProvider {
        public EsCl(FabricDataOutput output) {
            super(output, "es_cl");
        }
    }

    public static class EsEs extends SpanishLanguageProvider {
        public EsEs(FabricDataOutput output) {
            super(output, "es_es");
        }
        @Override
        public void generateTranslations(TranslationBuilder tb) {
            generateSpanishTranslations(tb, false);
        }
    }

    public static class EsMx extends SpanishLanguageProvider {
        public EsMx(FabricDataOutput output) {
            super(output, "es_mx");
        }
    }

    public static class EsUy extends SpanishLanguageProvider {
        public EsUy(FabricDataOutput output) {
            super(output, "es_uy");
        }
    }

    public static class EsVe extends SpanishLanguageProvider {
        public EsVe(FabricDataOutput output) {
            super(output, "es_ve");
        }
    }

    public static void generateSpanishTranslations(FabricLanguageProvider.TranslationBuilder tb, Boolean pastilla) {
        // Status Effects
        tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Poder Femenino");

        // Controls
        tb.add("category.estrogen", "Estrogen");
        tb.add("key.estrogen.dash", "Activar Dash");

        // Items
        String pillString = pastilla ? "Pastilla" : "Pildora";

        tb.add("itemGroup.estrogen", "Estrogen");
        tb.add(EstrogenItems.ESTROGEN_PILL.get(), pillString + " de Estrógeno");
        tb.add(EstrogenItems.ESTROGEN_PATCHES.get(), "Parche de Estrógeno");
        tb.add("item.estrogen.estrogen_patches_plural", "Parches de Estrógeno");
        tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Parche de Estrógeno Incompleto");
        tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL.get(), pillString + " de Estrógeno de Vidrio");
        tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get(), "Galleta de Pepitas de Estrógeno");
        tb.add(EstrogenItems.BALLS.get(), "Bolas");
        tb.add(EstrogenItems.HORSE_URINE_BOTTLE.get(), "Botella de Orina de Caballo");
        tb.add(EstrogenItems.TESTOSTERONE_CHUNK.get(), "Trozo de Testosterona");
        tb.add(EstrogenItems.TESTOSTERONE_POWDER.get(), "Polvo de Testosterona");
        tb.add(EstrogenItems.USED_FILTER.get(), "Filtro usado");
        tb.add(EstrogenItems.INCOMPLETE_UWU.get(), "UwU Incompleto");
        // Buckets
        tb.add(EstrogenFluidItems.LIQUID_ESTROGEN_BUCKET.get(), "Cubo de Estrógeno Líquido");
        tb.add(EstrogenFluidItems.HORSE_URINE_BUCKET.get(), "Cubo de Orina de Caballo");
        tb.add(EstrogenFluidItems.FILTRATED_HORSE_URINE_BUCKET.get(), "Cubo de Orina de Caballo Filtrada");
        tb.add(EstrogenFluidItems.MOLTEN_SLIME_BUCKET.get(), "Cubo de Slime Fundido");
        tb.add(EstrogenFluidItems.MOLTEN_AMETHYST_BUCKET.get(), "Cubo de Amatista Fundida");
        tb.add(EstrogenFluidItems.TESTOSTERONE_MIXTURE_BUCKET.get(), "Cubo de Mezcla de Testosterona");

        // Blocks
        tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrifugadora");
        // Fluids
        tb.add("fluid.estrogen.liquid_estrogen", "Estrógeno Líquido");
        tb.add("fluid.estrogen.horse_urine", "Orina de Caballo");
        tb.add("fluid.estrogen.filtrated_horse_urine", "Orina de Caballo Filtrada");
        tb.add("fluid.estrogen.molten_slime", "Slime Fundido");
        tb.add("fluid.estrogen.molten_amethyst", "Amatista Fundida");
        tb.add("fluid.estrogen.testosterone_mixture", "Mezcla de Testosterona");

        // Sounds
        tb.add("subtitles.estrogen.dash", "Dash de Poder Femenino");

        // Death
        tb.add("death.attack.girlpower", "%s se empoderó demasiado fuerte");
        tb.add("death.attack.girlpower.player", "%s se empoderó demasiado fuerte");

        // Trinkets
        tb.add("trinkets.slot.legs.thighs", "Muslos");
        // Curios
        tb.add("curios.identifier.thighs", "Muslos");

        // REI
        tb.add("create.recipe.centrifuging", "Centrifugando");

        // Enchantments
        tb.add(EstrogenEnchantments.UWUFYING_CURSE.get(), "Maldición de UwUificación");
        tb.add("enchantment.estrogen.uwufy_curse.desc", "UwUfica tus mensajes de chat >///<");

        // EMI
        tb.add("emi.category.estrogen.centrifuging", "Centrifugando");

        // Tags
        // Items
        tb.add("tag.item.trinkets.legs.thighs", "Muslos");
        tb.add("tag.item.estrogen.uwufying", "UwUificación");
        tb.add("tag.item.curios.thighs", "Muslos");
        tb.add("tag.item.estrogen.copper_plates", "Placas De Cobre");

        // Ponder
        // Centrifuge
        tb.add("estrogen.ponder.intro.header", "Requisitos de la Centrifugadora");
        tb.add("estrogen.ponder.intro.text_1", "¡La centrifugadora necesita la velocidad máxima (256 RPM) para funcionar!");
        tb.add("estrogen.ponder.basic.header", "Cómo utilizar la Centrifugadora");
        tb.add("estrogen.ponder.basic.text_1", "La Centrifugadora no tiene inventario, ¡tendrás que colocar recipientes de fluidos a su alrededor para que funcione!");
        tb.add("estrogen.ponder.basic.text_2", "Puedes introducir fluidos desde la parte inferior");
        tb.add("estrogen.ponder.basic.text_3", "Y extraerlos por la parte superior");

        // Attributes
        tb.add("attribute.name.estrogen.dash_level", "Nivel Del Tablero");
        tb.add("attribute.name.estrogen.boob_growing_start_time", "Pecho tiempo de inicio");
        tb.add("attribute.name.estrogen.boob_initial_size", "Pecho tamaño inicial");
    }

    public static class SpanishLanguageProvider extends FabricLanguageProvider {
        public SpanishLanguageProvider(FabricDataOutput output, String language) {
            super(output, language);
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            generateSpanishTranslations(tb, true);
        }
    }
}
