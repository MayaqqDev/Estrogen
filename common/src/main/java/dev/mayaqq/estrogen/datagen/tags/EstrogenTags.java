package dev.mayaqq.estrogen.datagen.tags;

import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class EstrogenTags {
    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public static final TagKey<Item> THIGHS = TagKey.create(Registry.ITEM.key(), new ResourceLocation("trinkets", "legs/thighs"));
        public static final TagKey<Item> CURIOS_THIGHS = TagKey.create(Registry.ITEM.key(), new ResourceLocation("curios", "thighs"));
        public static final TagKey<Item> MUSIC_DISCS = TagKey.create(Registry.ITEM.key(), mcId("music_discs"));
        public static final TagKey<Item> UWUFYING = TagKey.create(Registry.ITEM.key(), new ResourceLocation("estrogen", "uwufying"));
        public static final TagKey<Item> COPPER_PLATES = TagKey.create(Registry.ITEM.key(), new ResourceLocation("estrogen", "copper_plates"));

        public ItemTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get());
            getOrCreateTagBuilder(UWUFYING)
                    .add(EstrogenItems.UWU.get());
            getOrCreateTagBuilder(COPPER_PLATES)
                    .addOptionalTag(AllTags.forgeItemTag("copper_plates"))
                    .addOptionalTag(AllTags.optionalTag(Registry.ITEM, new ResourceLocation("forge", "plates/copper")));
            getOrCreateTagBuilder(CURIOS_THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get());
            getOrCreateTagBuilder(MUSIC_DISCS)
                    .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get());
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {

        public static final TagKey<Block> PICKAXE_MINABLE = TagKey.create(Registry.BLOCK.key(), new ResourceLocation("minecraft", "mineable/pickaxe"));

        public BlockTags(FabricDataGenerator output) {
            super(output);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(PICKAXE_MINABLE)
                    .add(EstrogenBlocks.CENTRIFUGE.get());
        }
    }

    public static class FluidTags extends FabricTagProvider.FluidTagProvider {

        // vanilla
        public static final TagKey<Fluid> WATER = TagKey.create(Registry.FLUID.key(), mcId("water"));
        public static final TagKey<Fluid> LAVA = TagKey.create(Registry.FLUID.key(), mcId("lava"));

        public FluidTags(FabricDataGenerator output) {
            super(output);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(WATER)
                    .add(EstrogenFluids.LIQUID_ESTROGEN.get()).add(EstrogenFluids.LIQUID_ESTROGEN_FLOWING.get())
                    .add(EstrogenFluids.HORSE_URINE.get()).add(EstrogenFluids.HORSE_URINE_FLOWING.get())
                    .add(EstrogenFluids.FILTRATED_HORSE_URINE.get()).add(EstrogenFluids.FILTRATED_HORSE_URINE_FLOWING.get())
                    .add(EstrogenFluids.TESTOSTERONE_MIXTURE.get()).add(EstrogenFluids.TESTOSTERONE_MIXTURE_FLOWING.get());

            getOrCreateTagBuilder(LAVA)
                    .add(EstrogenFluids.MOLTEN_AMETHYST.get()).add(EstrogenFluids.MOLTEN_AMETHYST_FLOWING.get())
                    .add(EstrogenFluids.MOLTEN_SLIME.get()).add(EstrogenFluids.MOLTEN_SLIME_FLOWING.get());
        }
    }

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {
        public EntityTypeTags(FabricDataGenerator output) {
            super(output);
        }

        @Override
        protected void generateTags() {}
    }

    private static ResourceLocation mcId(String path) {
        return new ResourceLocation("minecraft", path);
    }
}
