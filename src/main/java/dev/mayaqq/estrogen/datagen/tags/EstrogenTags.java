package dev.mayaqq.estrogen.datagen.tags;

import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EstrogenTags {
    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public static final TagKey<Item> THIGHS = TagKey.of(Registry.ITEM_KEY, new Identifier("trinkets", "legs/thighs"));
        public static final TagKey<Item> UWUFYING = TagKey.of(Registry.ITEM_KEY, new Identifier("estrogen", "uwufying"));

        public ItemTags(FabricDataGenerator fdg) {
            super(fdg);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES);
            getOrCreateTagBuilder(UWUFYING)
                    .add(EstrogenItems.UWU);
        }

        @Override
        public String getName() {
            return "Estrogen Item Tags";
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {

        public static final TagKey<Block> PICKAXE_MINEABLE = TagKey.of(Registry.BLOCK_KEY, mcId("mineable/pickaxe"));

        public BlockTags(FabricDataGenerator fdg) {
            super(fdg);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(PICKAXE_MINEABLE)
                    .add(EstrogenBlocks.CENTRIFUGE.get());
        }

        @Override
        public String getName() {
            return "Estrogen Block Tags";
        }
    }

    public static class FluidTags extends FabricTagProvider.FluidTagProvider {

        // vanilla
        public static final TagKey<Fluid> WATER = TagKey.of(Registry.FLUID_KEY, mcId("water"));
        public static final TagKey<Fluid> LAVA = TagKey.of(Registry.FLUID_KEY, mcId("lava"));

        public FluidTags(FabricDataGenerator fdg) {
            super(fdg);
        }

        @Override
        protected void generateTags() {
            getOrCreateTagBuilder(WATER)
                    .add(EstrogenFluids.LIQUID_ESTROGEN.still()).add(EstrogenFluids.LIQUID_ESTROGEN.flowing())
                    .add(EstrogenFluids.HORSE_URINE.still()).add(EstrogenFluids.HORSE_URINE.flowing())
                    .add(EstrogenFluids.FILTRATED_HORSE_URINE.still()).add(EstrogenFluids.FILTRATED_HORSE_URINE.flowing())
                    .add(EstrogenFluids.TESTOSTERONE_MIXTURE.still()).add(EstrogenFluids.TESTOSTERONE_MIXTURE.flowing());

            getOrCreateTagBuilder(LAVA)
                    .add(EstrogenFluids.MOLTEN_AMETHYST.still()).add(EstrogenFluids.MOLTEN_AMETHYST.flowing())
                    .add(EstrogenFluids.MOLTEN_SLIME.still()).add(EstrogenFluids.MOLTEN_SLIME.flowing());
        }
        @Override
        public String getName() {
            return "Estrogen Fluid Tags";
        }
    }

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {
        public EntityTypeTags(FabricDataGenerator fdg) {
            super(fdg);
        }

        @Override
        protected void generateTags() {}

        @Override
        public String getName() {
            return "Estrogen Entity Type Tags";
        }
    }

    private static Identifier mcId(String path) {
        return new Identifier("minecraft", path);
    }
}
