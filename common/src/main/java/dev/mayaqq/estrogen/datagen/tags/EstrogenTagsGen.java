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
            getOrCreateTagBuilder(EstrogenTags.Items.COOKIES)
                    .add(EstrogenItems.ESTROGEN_CHIP_COOKIE.get())
                    .add(Items.COOKIE);
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

            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_OLD)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
            getOrCreateTagBuilder(EstrogenTags.Blocks.MAGNET_12_OLD)
                    .add(EstrogenBlocks.DORMANT_DREAM_BLOCK.get());
        }
    }

    public static class FluidTags extends FabricTagProvider.FluidTagProvider {

        public FluidTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
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
