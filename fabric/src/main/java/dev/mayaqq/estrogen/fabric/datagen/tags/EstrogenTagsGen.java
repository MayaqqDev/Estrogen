package dev.mayaqq.estrogen.fabric.datagen.tags;

import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class EstrogenTagsGen {
    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public ItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(EstrogenTags.Items.THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get())
                    .add(EstrogenItems.THIGH_HIGHS.get());
            getOrCreateTagBuilder(EstrogenTags.Items.UWUFYING)
                    .add(EstrogenItems.UWU.get());
            getOrCreateTagBuilder(EstrogenTags.Items.CURIOS_THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get())
                    .add(EstrogenItems.THIGH_HIGHS.get());
            getOrCreateTagBuilder(EstrogenTags.Items.MUSIC_DISCS)
                    .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get());
            getOrCreateTagBuilder(EstrogenTags.Items.LAVA_BUCKETS)
                    .add(EstrogenItems.MOLTEN_SLIME_BUCKET.get())
                    .add(EstrogenItems.MOLTEN_AMETHYST_BUCKET.get());
            getOrCreateTagBuilder(EstrogenTags.Items.COOKIES)
                    .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get())
                    .add(Items.COOKIE);
            getOrCreateTagBuilder(EstrogenTags.Items.LEATHER_ITEMS)
                    .add(Items.LEATHER)
                    .add(Items.LEATHER_BOOTS)
                    .add(Items.LEATHER_CHESTPLATE)
                    .add(Items.LEATHER_HELMET)
                    .add(Items.LEATHER_LEGGINGS)
                    .add(Items.LEATHER_HORSE_ARMOR);
            getOrCreateTagBuilder(EstrogenTags.Items.LIGHT_EMITTERS)
                    .add(Items.TORCH)
                    .add(Items.TORCHFLOWER)
                    .add(Items.LANTERN)
                    .add(Items.SOUL_LANTERN)
                    .add(Items.CANDLE);
            getOrCreateTagBuilder(AllTags.AllItemTags.SEATS.tag)
                    .add(EstrogenItems.MOTH_SEAT.get());
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {
        public BlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(EstrogenTags.Blocks.PICKAXE_MINABLE)
                    .add(EstrogenBlocks.CENTRIFUGE.get())
                    .add(EstrogenBlocks.COOKIE_JAR.get())
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get())
                    .add(EstrogenBlocks.ESTROGEN_PILL_BLOCK.get());
            getOrCreateTagBuilder(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE)
                    .add(EstrogenBlocks.MOTH_SEAT.get());

            getOrCreateTagBuilder(AllTags.AllBlockTags.SEATS.tag)
                    .add(EstrogenBlocks.MOTH_SEAT.get());

            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_OLD)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12_OLD)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
            getOrCreateTagBuilder(net.minecraft.tags.BlockTags.WOOL)
                    .add(EstrogenBlocks.MOTH_WOOL.get());
        }
    }

    public static class FluidTags<T extends EstrogenTagsInterface> extends FabricTagProvider.FluidTagProvider {

        private T t;
        public FluidTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, T t) {
            super(output, completableFuture);
            this.t = t;
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(EstrogenTags.Fluids.WATER)
                    .add(EstrogenFluids.HORSE_URINE.get())
                    .add(EstrogenFluids.FILTRATED_HORSE_URINE.get());
            getOrCreateTagBuilder(EstrogenTags.Fluids.URINE)
                    .add(EstrogenFluids.HORSE_URINE.get())
                    .add(EstrogenFluids.FILTRATED_HORSE_URINE.get());
            getOrCreateTagBuilder(AllTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_BLASTING.tag)
                    .add(EstrogenFluids.MOLTEN_SLIME.get());

            if (t instanceof EstrogenTagsFabricImpl) {
                getOrCreateTagBuilder(EstrogenTags.Fluids.WATER)
                        .add(EstrogenFluids.LIQUID_ESTROGEN.get())
                        .add(EstrogenFluids.LIQUID_ESTROGEN_FLOWING.get())
                        .add(EstrogenFluids.HORSE_URINE_FLOWING.get())
                        .add(EstrogenFluids.FILTRATED_HORSE_URINE_FLOWING.get())
                        .add(EstrogenFluids.TESTOSTERONE_MIXTURE.get())
                        .add(EstrogenFluids.TESTOSTERONE_MIXTURE_FLOWING.get());
                getOrCreateTagBuilder(EstrogenTags.Fluids.LAVA)
                        .add(EstrogenFluids.MOLTEN_SLIME.get())
                        .add(EstrogenFluids.MOLTEN_SLIME_FLOWING.get())
                        .add(EstrogenFluids.MOLTEN_AMETHYST.get())
                        .add(EstrogenFluids.MOLTEN_AMETHYST_FLOWING.get());
            }
        }

        public static FluidTags<?> buildFabric(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            return new FluidTags<>(output, completableFuture, new EstrogenTagsFabricImpl());
        }

        public static FluidTags<?> buildForge(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            return new FluidTags<>(output, completableFuture, new EstrogenTagsForgeImpl());
        }

        @Override
        public String getName() {
            return this.t.getName(super.getName());
        }
    }

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {
        public EntityTypeTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(EstrogenTags.Entities.URINE_GIVING)
                    .add(EntityType.HORSE)
                    .add(EntityType.ZOMBIE_HORSE)
                    .add(EntityType.DONKEY)
                    .add(EntityType.MULE);
        }
    }
}
