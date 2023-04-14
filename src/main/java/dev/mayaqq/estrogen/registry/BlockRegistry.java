package dev.mayaqq.estrogen.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class BlockRegistry {
    public static Block MOLTEN_SLIME;
    public static Block TESTOSTERONE_MIXTURE;
    public static Block LIQUID_ESTROGEN;
    public static void register() {
        MOLTEN_SLIME = Registry.register(Registry.BLOCK, id("molten_slime"), new FluidBlock(FluidRegistry.STILL_MOLTEN_SLIME, Block.Settings.copy(Blocks.LAVA)));
        TESTOSTERONE_MIXTURE = Registry.register(Registry.BLOCK, id("testosterone_mixture"), new FluidBlock(FluidRegistry.STILL_TESTOSTERONE_MIXTURE, Block.Settings.copy(Blocks.WATER)));
        LIQUID_ESTROGEN = Registry.register(Registry.BLOCK, id("liquid_estrogen"), new FluidBlock(FluidRegistry.STILL_LIQUID_ESTROGEN, Block.Settings.copy(Blocks.WATER)));
    }
}
