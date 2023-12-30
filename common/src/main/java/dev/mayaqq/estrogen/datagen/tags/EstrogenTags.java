package dev.mayaqq.estrogen.datagen.tags;

import dev.mayaqq.estrogen.registry.common.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import dev.mayaqq.estrogen.registry.common.EstrogenItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class EstrogenTags {
    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public static final TagKey<Item> THIGHS = TagKey.of(Registries.ITEM.getKey(), new Identifier("trinkets", "legs/thighs"));
        public static final TagKey<Item> UWUFYING = TagKey.of(Registries.ITEM.getKey(), new Identifier("estrogen", "uwufying"));

        public ItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES);
            getOrCreateTagBuilder(UWUFYING)
                    .add(EstrogenItems.UWU);
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {

        public static final TagKey<Block> PICKAXE_MINABLE = TagKey.of(Registries.BLOCK.getKey(), new Identifier("minecraft", "mineable/pickaxe"));

        public BlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(PICKAXE_MINABLE)
                    .add(EstrogenBlocks.CENTRIFUGE.get());
        }
    }

    public static class FluidTags extends FabricTagProvider.FluidTagProvider {

        // vanilla
        public static final TagKey<Fluid> WATER = TagKey.of(Registries.FLUID.getKey(), mcId("water"));
        public static final TagKey<Fluid> LAVA = TagKey.of(Registries.FLUID.getKey(), mcId("lava"));

        public FluidTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(WATER)
                    .add(EstrogenFluids.LIQUID_ESTROGEN.still()).add(EstrogenFluids.LIQUID_ESTROGEN.flowing())
                    .add(EstrogenFluids.HORSE_URINE.still()).add(EstrogenFluids.HORSE_URINE.flowing())
                    .add(EstrogenFluids.FILTRATED_HORSE_URINE.still()).add(EstrogenFluids.FILTRATED_HORSE_URINE.flowing())
                    .add(EstrogenFluids.TESTOSTERONE_MIXTURE.still()).add(EstrogenFluids.TESTOSTERONE_MIXTURE.flowing());

            getOrCreateTagBuilder(LAVA)
                    .add(EstrogenFluids.MOLTEN_AMETHYST.still()).add(EstrogenFluids.MOLTEN_AMETHYST.flowing())
                    .add(EstrogenFluids.MOLTEN_SLIME.still()).add(EstrogenFluids.MOLTEN_SLIME.flowing());
        }
    }

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {
        public EntityTypeTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider arg) {}
    }

    private static Identifier mcId(String path) {
        return new Identifier("minecraft", path);
    }
}
