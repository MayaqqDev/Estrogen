package dev.mayaqq.estrogen.datagen.translations;

import dev.mayaqq.estrogen.registry.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class PtBr extends FabricLanguageProvider {

    public PtBr(FabricDataOutput output) {
        super(output, "pt_br");
    }

    @Override
    public void generateTranslations(TranslationBuilder tb) {
        // Status Effects
        tb.add(EstrogenEffects.ESTROGEN_EFFECT.get(), "Poder Feminino");

        // Controls
        tb.add("category.estrogen", "Estrogênio");
        tb.add("key.estrogen.dash", "Ativar Dash");

        // Items
        tb.add("itemGroup.estrogen.estrogen", "Estrogênio");
        tb.add(EstrogenCreateItems.ESTROGEN_PILL.get(), "Comprimido de Estrogênio");
        tb.add(EstrogenCreateItems.ESTROGEN_PATCHES.get(), "Adesivo de Estrogênio");
        tb.add("item.estrogen.estrogen_patches_plural", "Adesivos de Estrogênio");
        tb.add(EstrogenCreateItems.INCOMPLETE_ESTROGEN_PATCH.get(), "Adesivo Incompleto de Estrogênio");
        tb.add(EstrogenCreateItems.CRYSTAL_ESTROGEN_PILL.get(), "Pilula de Estrogênio Cristalizado");
        tb.add(EstrogenCreateItems.ESTROGEN_CHIP_COOKIE.get(), "Cookie de Estrogênio");
        tb.add("item.estrogen.estrogen_chip_cookie.desc", "erora - G03C");
        tb.add(EstrogenCreateItems.BALLS.get(), "Bolas");
        tb.add(EstrogenCreateItems.HORSE_URINE_BOTTLE.get(), "Garrafa de Urina de Cavalo");
        tb.add(EstrogenCreateItems.TESTOSTERONE_CHUNK.get(), "Pedaço de Testosterona");
        tb.add(EstrogenCreateItems.TESTOSTERONE_POWDER.get(), "Pó de Testosterona");
        tb.add(EstrogenCreateItems.USED_FILTER.get(), "Filtro Usado");
        tb.add(EstrogenCreateItems.UWU.get(), ":3");
        tb.add("item.estrogen.uwu.tooltip", "§r§dUwU");
        tb.add(EstrogenCreateItems.INCOMPLETE_UWU.get(), "UwU Incompleto");
        // Buckets
        tb.add(EstrogenItems.LIQUID_ESTROGEN_BUCKET.get(), "Balde de Estrogênio Liquido");
        tb.add(EstrogenItems.HORSE_URINE_BUCKET.get(), "Balde de Urina de Cavalo");
        tb.add(EstrogenItems.FILTRATED_HORSE_URINE_BUCKET.get(), "Balde de Urina de Cavalo Filtrada");
        tb.add(EstrogenItems.MOLTEN_SLIME_BUCKET.get(), "Balde de Slime Derretido");
        tb.add(EstrogenItems.MOLTEN_AMETHYST_BUCKET.get(), "Balde de Ametista Derretida");
        tb.add(EstrogenItems.TESTOSTERONE_MIXTURE_BUCKET.get(), "Balde de Mistura de Testosterona");

        // Blocks
        tb.add(EstrogenCreateBlocks.CENTRIFUGE.get(), "Centrífuga");
        tb.add(EstrogenBlocks.LIQUID_ESTROGEN_BLOCK.get(), "Estrogênio Líquido");
        tb.add(EstrogenBlocks.HORSE_URINE_BLOCK.get(), "Urina de Cavalo");
        tb.add(EstrogenBlocks.FILTRATED_HORSE_URINE_BLOCK.get(), "Urina de Cavalo Filtrada");
        tb.add(EstrogenBlocks.MOLTEN_SLIME_BLOCK.get(), "Slime Derretido");
        tb.add(EstrogenBlocks.MOLTEN_AMETHYST_BLOCK.get(), "Ametista Derretida");
        tb.add(EstrogenBlocks.TESTOSTERONE_MIXTURE_BLOCK.get(), "Mistura de Testosterona");

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
