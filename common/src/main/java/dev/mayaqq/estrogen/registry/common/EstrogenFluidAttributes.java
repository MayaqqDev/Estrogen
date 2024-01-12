package dev.mayaqq.estrogen.registry.common;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.registry.registries.Registrar;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EstrogenFluidAttributes {

    public static Registrar<Item> FLUID_ITEMS = Estrogen.MANAGER.get().get(Registries.ITEM);
    public static Registrar<Block> FLUID_BLOCKS = Estrogen.MANAGER.get().get(Registries.BLOCK);

    public static final SimpleArchitecturyFluidAttributes MOLTEN_SLIME = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.MOLTEN_SLIME_FLOWING, () -> EstrogenFluids.MOLTEN_SLIME)
            .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id("molten_slime" + "_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.MOLTEN_SLIME, BlockBehaviour.Properties.copy(Blocks.LAVA))))
            .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id("molten_slime" + "_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.MOLTEN_SLIME, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB))))
            .flowingTexture(Estrogen.id("block/blank_lava/blank_lava_flow"))
            .sourceTexture(Estrogen.id("block/blank_lava/blank_lava_still"))
            .color(144238144)
            ;

    public static final SimpleArchitecturyFluidAttributes TESTOSTERONE_MIXTURE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.TESTOSTERONE_MIXTURE_FLOWING, () -> EstrogenFluids.TESTOSTERONE_MIXTURE)
            .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id("testosterone_mixture" + "_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.TESTOSTERONE_MIXTURE, BlockBehaviour.Properties.copy(Blocks.WATER))))
            .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id("testosterone_mixture" + "_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.TESTOSTERONE_MIXTURE, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB))))
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(154148010)
            ;

    public static final SimpleArchitecturyFluidAttributes LIQUID_ESTROGEN = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.LIQUID_ESTROGEN_FLOWING, () -> EstrogenFluids.LIQUID_ESTROGEN)
            .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id("liquid_estrogen" + "_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.LIQUID_ESTROGEN, BlockBehaviour.Properties.copy(Blocks.WATER))))
            .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id("liquid_estrogen" + "_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.LIQUID_ESTROGEN, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB))))
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(104164161)
            ;

    public static final SimpleArchitecturyFluidAttributes FILTRATED_HORSE_URINE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.FILTRATED_HORSE_URINE_FLOWING, () -> EstrogenFluids.FILTRATED_HORSE_URINE)
            .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id("filtrated_horse_urine" + "_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.FILTRATED_HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER))))
            .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id("filtrated_horse_urine" + "_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.FILTRATED_HORSE_URINE, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB))))
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(0xE1E114)
            ;

    public static final SimpleArchitecturyFluidAttributes HORSE_URINE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.HORSE_URINE_FLOWING, () -> EstrogenFluids.HORSE_URINE)
            .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id("liquid_estrogen" + "_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER))))
            .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id("liquid_estrogen" + "_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.HORSE_URINE, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB))))
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(0x8C8B05)
            ;

    public static final SimpleArchitecturyFluidAttributes MOLTEN_AMETHYST = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.MOLTEN_AMETHYST_FLOWING, () -> EstrogenFluids.MOLTEN_AMETHYST)
            .blockSupplier(() -> FLUID_BLOCKS.register(Estrogen.id("molten_amethyst" + "_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.MOLTEN_AMETHYST, BlockBehaviour.Properties.copy(Blocks.LAVA))))
            .bucketItemSupplier(() -> FLUID_ITEMS.register(Estrogen.id("molten_amethyst" + "_bucket"), () -> new ArchitecturyBucketItem(EstrogenFluids.MOLTEN_AMETHYST, new Item.Properties().arch$tab(EstrogenCreativeTab.ESTROGEN_TAB))))
            .flowingTexture(Estrogen.id("block/blank_lava/blank_lava_flow"))
            .sourceTexture(Estrogen.id("block/blank_lava/blank_lava_still"))
            .color(0xAE7AFD)
            ;

    public static void register() {}

}
