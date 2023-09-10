package dev.mayaqq.estrogen.datagen.translations;

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
            // Status Effects
                tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Girl Power");

            // Controls
            tb.add("category.estrogen", "Estrogen");
                tb.add("key.estrogen.dash", "Activate Dash");

            // Items
            tb.add(EstrogenItems.ESTROGEN_GROUP_KEY, "Estrogen");
                tb.add(EstrogenItems.ESTROGEN_PILL, "Estrogen Pill");
                tb.add(EstrogenItems.ESTROGEN_PATCHES, "Estrogen Patch");
                tb.add("item.estrogen.estrogen_patches_plural", "Estrogen Patches");
                tb.add(EstrogenItems.INCOMPLETE_ESTROGEN_PATCH, "Incomplete Estrogen Patch");
                tb.add(EstrogenItems.CRYSTAL_ESTROGEN_PILL, "Crystal Estrogen Pill");
                tb.add(EstrogenItems.ESTROGEN_CHIP_COOKIE, "Estrogen Chip Cookie");
                tb.add(EstrogenItems.BALLS, "Balls");
                tb.add(EstrogenItems.HORSE_URINE_BOTTLE, "Horse Urine Bottle");
                tb.add(EstrogenItems.TESTOSTERONE_CHUNK, "Testosterone Chunk");
                tb.add(EstrogenItems.TESTOSTERONE_POWDER, "Testosterone Powder");
                tb.add(EstrogenItems.USED_FILTER, "Used Filter");
                tb.add(EstrogenItems.UWU, ":3");
                    tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
                tb.add(EstrogenItems.INCOMPLETE_UWU, "Incomplete UwU");
                // Buckets
                tb.add(EstrogenFluids.LIQUID_ESTROGEN.bucket(), "Bucket of Liquid Estrogen");
                tb.add(EstrogenFluids.HORSE_URINE.bucket(), "Bucket of Horse Urine");
                tb.add(EstrogenFluids.FILTRATED_HORSE_URINE.bucket(), "Bucket of Filtrated Horse Urine");
                tb.add(EstrogenFluids.MOLTEN_SLIME.bucket(), "Bucket of Molten Slime");
                tb.add(EstrogenFluids.MOLTEN_AMETHYST.bucket(), "Bucket of Molten Amethyst");
                tb.add(EstrogenFluids.TESTOSTERONE_MIXTURE.bucket(), "Bucket of Testosterone Mixture");


            // Blocks
                tb.add(EstrogenBlocks.CENTRIFUGE.get(), "Centrifuge");
                tb.add(EstrogenFluids.LIQUID_ESTROGEN_BLOCK, "Liquid Estrogen");
                tb.add(EstrogenFluids.HORSE_URINE_BLOCK, "Horse Urine");
                tb.add(EstrogenFluids.FILTRATED_HORSE_URINE_BLOCK, "Filtrated Horse Urine");
                tb.add(EstrogenFluids.MOLTEN_SLIME_BLOCK, "Molten Slime");
                tb.add(EstrogenFluids.MOLTEN_AMETHYST_BLOCK, "Molten Amethyst");
                tb.add(EstrogenFluids.TESTOSTERONE_MIXTURE_BLOCK, "Testosterone Mixture");

            // Sounds
                tb.add("subtitles.estrogen.dash", "Girl Power Dash");

            // Death
                tb.add("death.attack.girlpower", "%s Girlbossed too hard");

            // Trinkets
                tb.add("trinkets.slot.legs.thighs", "Thighs");

            // REI
                tb.add("create.recipe.centrifuging", "Centrifuging");

            // Enchantments
                tb.add(EstrogenEnchantments.UWUFYING_CURSE, "Curse of Uwufying");

            // EMI
                tb.add("emi.category.estrogen.centrifuging", "Centrifuging");

            // Tags
                // Items
                tb.add("tag.item.trinkets.legs.thighs", "Thighs");
                tb.add("tag.item.estrogen.uwufying", "Uwufying");

        }
    }

    public static class FrFr extends FabricLanguageProvider {

        public FrFr(FabricDataOutput output) {
            super(output, "fr_fr");
        }

        @Override
        public void generateTranslations(TranslationBuilder tb) {
            // Status Effects
                tb.add(EstrogenEffects.ESTROGEN_EFFECT, "Girl Power");

            // Controls
                tb.add("category.estrogen", "Oestrogènes");
                tb.add("key.estrogen.dash", "Activer/Désactiver Dash");

            // Items
                tb.add(EstrogenItems.ESTROGEN_GROUP_KEY, "Oestrogène");
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

            // EMI
                tb.add("emi.category.estrogen.centrifuging", "Centrifugation");

            // Tags
                // Item
                tb.add("tag.item.trinkets.legs.thighs", "Cuisses");
                tb.add("tag.item.estrogen.uwufying", "Uwufying");
        }
    }
}