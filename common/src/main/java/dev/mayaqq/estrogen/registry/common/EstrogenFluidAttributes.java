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
            .overlayTexture(Estrogen.id("block/blank_lava/blank_lava_flow"))
            .temperature(1300)
            .color(0xFF90EE90)
            ;

    public static final SimpleArchitecturyFluidAttributes TESTOSTERONE_MIXTURE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.TESTOSTERONE_MIXTURE_FLOWING, () -> EstrogenFluids.TESTOSTERONE_MIXTURE)
            .blockSupplier(() -> EstrogenFluidBlocks.TESTOSTERONE_MIXTURE_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.TESTOSTERONE_MIXTURE_BUCKET)
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .overlayTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .color(0xFF301CAA)
            ;

    public static final SimpleArchitecturyFluidAttributes LIQUID_ESTROGEN = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.LIQUID_ESTROGEN_FLOWING, () -> EstrogenFluids.LIQUID_ESTROGEN)
            .blockSupplier(() -> EstrogenFluidBlocks.LIQUID_ESTROGEN_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.LIQUID_ESTROGEN_BUCKET)
            .flowingTexture(Estrogen.id("block/liquid_estrogen/liquid_estrogen_flow"))
            .sourceTexture(Estrogen.id("block/liquid_estrogen/liquid_estrogen_still"))
            .overlayTexture(Estrogen.id("block/liquid_estrogen/liquid_estrogen_flow"))
            .color(0xFFFFFFFF)
            ;

    public static final SimpleArchitecturyFluidAttributes FILTRATED_HORSE_URINE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.FILTRATED_HORSE_URINE_FLOWING, () -> EstrogenFluids.FILTRATED_HORSE_URINE)
            .blockSupplier(() -> EstrogenFluidBlocks.FILTRATED_HORSE_URINE_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.FILTRATED_HORSE_URINE_BUCKET)
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .overlayTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .color(0xFFE1E114)
            ;

    public static final SimpleArchitecturyFluidAttributes HORSE_URINE = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.HORSE_URINE_FLOWING, () -> EstrogenFluids.HORSE_URINE)
            .blockSupplier(() -> EstrogenFluidBlocks.HORSE_URINE_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.HORSE_URINE_BUCKET)
            .flowingTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .sourceTexture(new ResourceLocation("minecraft", "block/water_still"))
            .overlayTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .color(0xFF8C8B05)
            ;

    public static final SimpleArchitecturyFluidAttributes MOLTEN_AMETHYST = SimpleArchitecturyFluidAttributes.ofSupplier(() -> EstrogenFluids.MOLTEN_AMETHYST_FLOWING, () -> EstrogenFluids.MOLTEN_AMETHYST)
            .blockSupplier(() -> EstrogenFluidBlocks.MOLTEN_AMETHYST_BLOCK)
            .bucketItemSupplier(() -> EstrogenFluidItems.MOLTEN_AMETHYST_BUCKET)
            .flowingTexture(Estrogen.id("block/blank_lava/blank_lava_flow"))
            .sourceTexture(Estrogen.id("block/blank_lava/blank_lava_still"))
            .overlayTexture(new ResourceLocation("minecraft", "block/water_flow"))
            .temperature(1300)
            .color(0xFFAE7AFD)
            ;

    public static void register() {}

}
