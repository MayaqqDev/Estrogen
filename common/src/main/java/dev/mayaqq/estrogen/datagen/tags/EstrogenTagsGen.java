package dev.mayaqq.estrogen.datagen.tags;

import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import dev.mayaqq.estrogen.registry.common.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class EstrogenTagsGen {
    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public ItemTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(EstrogenTags.Items.THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get());
            getOrCreateTagBuilder(EstrogenTags.Items.UWUFYING)
                    .add(EstrogenItems.UWU.get());
            getOrCreateTagBuilder(EstrogenTags.Items.COPPER_PLATES)
                    .addOptionalTag(AllTags.forgeItemTag("copper_plates"))
                    .addOptionalTag(AllTags.optionalTag(Registry.ITEM, new ResourceLocation("forge", "plates/copper")));
            getOrCreateTagBuilder(EstrogenTags.Items.CURIOS_THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get());
            getOrCreateTagBuilder(EstrogenTags.Items.MUSIC_DISCS)
                    .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get());
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {

        public BlockTags(FabricDataGenerator output) {
            super(output);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(EstrogenTags.Blocks.PICKAXE_MINABLE)
                    .add(EstrogenBlocks.CENTRIFUGE.get());
        }
    }

    public static class FluidTags extends FabricTagProvider.FluidTagProvider {

        public FluidTags(FabricDataGenerator output) {
            super(output);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(EstrogenTags.Fluids.WATER)
                    .add(EstrogenFluids.LIQUID_ESTROGEN.get()).add(EstrogenFluids.LIQUID_ESTROGEN_FLOWING.get())
                    .add(EstrogenFluids.HORSE_URINE.get()).add(EstrogenFluids.HORSE_URINE_FLOWING.get())
                    .add(EstrogenFluids.FILTRATED_HORSE_URINE.get()).add(EstrogenFluids.FILTRATED_HORSE_URINE_FLOWING.get())
                    .add(EstrogenFluids.TESTOSTERONE_MIXTURE.get()).add(EstrogenFluids.TESTOSTERONE_MIXTURE_FLOWING.get());

            getOrCreateTagBuilder(EstrogenTags.Fluids.LAVA)
                    .add(EstrogenFluids.MOLTEN_AMETHYST.get()).add(EstrogenFluids.MOLTEN_AMETHYST_FLOWING.get())
                    .add(EstrogenFluids.MOLTEN_SLIME.get()).add(EstrogenFluids.MOLTEN_SLIME_FLOWING.get());
        }
    }

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {
        public EntityTypeTags(FabricDataGenerator output) {
            super(output);
        }

        @Override
        protected void generateTags() {
        }
    }
}
