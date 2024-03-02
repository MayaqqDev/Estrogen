package dev.mayaqq.estrogen.datagen.tags;

import com.simibubi.create.AllTags;
import dev.mayaqq.estrogen.registry.EstrogenBlocks;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import dev.mayaqq.estrogen.registry.EstrogenItems;
import dev.mayaqq.estrogen.registry.EstrogenTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class EstrogenTagsGen {
    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public ItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(EstrogenTags.Items.THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get());
            getOrCreateTagBuilder(EstrogenTags.Items.UWUFYING)
                    .add(EstrogenItems.UWU.get());
            getOrCreateTagBuilder(EstrogenTags.Items.COPPER_PLATES)
                    .addOptionalTag(AllTags.forgeItemTag("copper_plates"))
                    .addOptionalTag(AllTags.optionalTag(BuiltInRegistries.ITEM, new ResourceLocation("forge", "plates/copper")));
            getOrCreateTagBuilder(EstrogenTags.Items.CURIOS_THIGHS)
                    .add(EstrogenItems.ESTROGEN_PATCHES.get());
            getOrCreateTagBuilder(EstrogenTags.Items.MUSIC_DISCS)
                    .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get());
            getOrCreateTagBuilder(EstrogenTags.Items.LAVA_BUCKETS)
                    .add(EstrogenItems.MOLTEN_SLIME_BUCKET.get())
                    .add(EstrogenItems.MOLTEN_AMETHYST_BUCKET.get());
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {
        public BlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            getOrCreateTagBuilder(EstrogenTags.Blocks.PICKAXE_MINABLE)
                    .add(EstrogenBlocks.CENTRIFUGE.get());
        }
    }

    public static class FluidTags extends FabricTagProvider.FluidTagProvider {

        public FluidTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
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
