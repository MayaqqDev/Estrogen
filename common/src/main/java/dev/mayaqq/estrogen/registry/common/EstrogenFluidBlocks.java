package dev.mayaqq.estrogen.registry.common;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EstrogenFluidBlocks {
    public static Registrar<Block> FLUID_BLOCKS = Estrogen.MANAGER.get().get(Registries.BLOCK);

    public static final RegistrySupplier<LiquidBlock> MOLTEN_SLIME_BLOCK = FLUID_BLOCKS.register(Estrogen.id("molten_slime_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.MOLTEN_SLIME, BlockBehaviour.Properties.copy(Blocks.LAVA)));
    public static final RegistrySupplier<LiquidBlock> TESTOSTERONE_MIXTURE_BLOCK = FLUID_BLOCKS.register(Estrogen.id("testosterone_mixture_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.TESTOSTERONE_MIXTURE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistrySupplier<LiquidBlock> LIQUID_ESTROGEN_BLOCK = FLUID_BLOCKS.register(Estrogen.id("liquid_estrogen_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.LIQUID_ESTROGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistrySupplier<LiquidBlock> FILTRATED_HORSE_URINE_BLOCK = FLUID_BLOCKS.register(Estrogen.id("filtrated_horse_urine_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.FILTRATED_HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistrySupplier<LiquidBlock> HORSE_URINE_BLOCK = FLUID_BLOCKS.register(Estrogen.id("horse_urine_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistrySupplier<LiquidBlock> MOLTEN_AMETHYST_BLOCK = FLUID_BLOCKS.register(Estrogen.id("molten_amethyst_block"), () -> new ArchitecturyLiquidBlock(EstrogenFluids.MOLTEN_AMETHYST, BlockBehaviour.Properties.copy(Blocks.LAVA)));

    public static void register() {}
}
