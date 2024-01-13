package dev.mayaqq.estrogen.registry.common;

import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.resources.ResourceLocation;

public class EstrogenFluidAttributes {
    public static final SimpleArchitecturyFluidAttributes MOLTEN_SLIME = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.MOLTEN_SLIME_FLOWING, () -> EstrogenFluids.MOLTEN_SLIME)
            .blockSupplier(() -> EstrogenFluidBlocks.MOLTEN_SLIME_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.MOLTEN_SLIME_BUCKET)
            .flowingTexture(Estrogen.id("block/blank_lava/blank_lava_flow"))
            .sourceTexture(Estrogen.id("block/blank_lava/blank_lava_still"))
            .color(144238144)
            ;

    public static final SimpleArchitecturyFluidAttributes TESTOSTERONE_MIXTURE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.TESTOSTERONE_MIXTURE_FLOWING, () -> EstrogenFluids.TESTOSTERONE_MIXTURE)
            .blockSupplier(() -> EstrogenFluidBlocks.TESTOSTERONE_MIXTURE_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.TESTOSTERONE_MIXTURE_BUCKET)
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(154148010)
            ;

    public static final SimpleArchitecturyFluidAttributes LIQUID_ESTROGEN = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.LIQUID_ESTROGEN_FLOWING, () -> EstrogenFluids.LIQUID_ESTROGEN)
            .blockSupplier(() -> EstrogenFluidBlocks.LIQUID_ESTROGEN_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.LIQUID_ESTROGEN_BUCKET)
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(104164161)
            ;

    public static final SimpleArchitecturyFluidAttributes FILTRATED_HORSE_URINE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.FILTRATED_HORSE_URINE_FLOWING, () -> EstrogenFluids.FILTRATED_HORSE_URINE)
            .blockSupplier(() -> EstrogenFluidBlocks.FILTRATED_HORSE_URINE_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.FILTRATED_HORSE_URINE_BUCKET)
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(0xE1E114)
            ;

    public static final SimpleArchitecturyFluidAttributes HORSE_URINE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.HORSE_URINE_FLOWING, () -> EstrogenFluids.HORSE_URINE)
            .blockSupplier(() -> EstrogenFluidBlocks.HORSE_URINE_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.HORSE_URINE_BUCKET)
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .color(0x8C8B05)
            ;

    public static final SimpleArchitecturyFluidAttributes MOLTEN_AMETHYST = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.MOLTEN_AMETHYST_FLOWING, () -> EstrogenFluids.MOLTEN_AMETHYST)
            .blockSupplier(() -> EstrogenFluidBlocks.MOLTEN_AMETHYST_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.MOLTEN_AMETHYST_BUCKET)
            .flowingTexture(Estrogen.id("block/blank_lava/blank_lava_flow"))
            .sourceTexture(Estrogen.id("block/blank_lava/blank_lava_still"))
            .color(0xAE7AFD)
            ;

    public static void register() {}

}
