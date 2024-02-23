package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.registry.blocks.fluids.EstrogenLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EstrogenBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, "estrogen");
    public static final ResourcefulRegistry<Block> TRANSPARENT_BLOCKS = ResourcefulRegistries.create(BLOCKS);

    public static final RegistryEntry<Block> MOLTEN_SLIME_BLOCK = BLOCKS.register("molten_slime", () -> new BotariumLiquidBlock(EstrogenFluidProperties.MOLTEN_SLIME, BlockBehaviour.Properties.copy(Blocks.LAVA)));
    public static final RegistryEntry<Block> TESTOSTERONE_MIXTURE_BLOCK = TRANSPARENT_BLOCKS.register("testosterone_mixture", () -> new BotariumLiquidBlock(EstrogenFluidProperties.TESTOSTERONE_MIXTURE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> LIQUID_ESTROGEN_BLOCK = TRANSPARENT_BLOCKS.register("liquid_estrogen", () -> new EstrogenLiquidBlock(EstrogenFluidProperties.LIQUID_ESTROGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> FILTRATED_HORSE_URINE_BLOCK = TRANSPARENT_BLOCKS.register("filtrated_horse_urine", () -> new BotariumLiquidBlock(EstrogenFluidProperties.FILTRATED_HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> HORSE_URINE_BLOCK = TRANSPARENT_BLOCKS.register("horse_urine", () -> new BotariumLiquidBlock(EstrogenFluidProperties.HORSE_URINE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> MOLTEN_AMETHYST_BLOCK = BLOCKS.register("molten_amethyst", () -> new BotariumLiquidBlock(EstrogenFluidProperties.MOLTEN_AMETHYST, BlockBehaviour.Properties.copy(Blocks.LAVA)));
}
