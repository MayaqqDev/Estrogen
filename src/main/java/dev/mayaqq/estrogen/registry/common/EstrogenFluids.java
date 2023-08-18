package dev.mayaqq.estrogen.registry.common;

import dev.mayaqq.estrogen.registry.common.fluids.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenFluids {
    public static FluidType MOLTEN_SLIME = register("molten_slime", new MoltenSlimeFluid.Still(), new MoltenSlimeFluid.Flowing());
    public static Block MOLTEN_SLIME_BLOCK = registerFluidBlock("molten_slime", MOLTEN_SLIME.still(), false);
    public static FluidType TESTOSTERONE_MIXTURE = register("testosterone_mixture", new TestosteroneMixtureFluid.Still(), new TestosteroneMixtureFluid.Flowing());
    public static Block TESTOSTERONE_MIXTURE_BLOCK = registerFluidBlock("testosterone_mixture", TESTOSTERONE_MIXTURE.still(), true);
    public static FluidType LIQUID_ESTROGEN = register("liquid_estrogen", new LiquidEstrogenFluid.Still(), new LiquidEstrogenFluid.Flowing());
    public static Block LIQUID_ESTROGEN_BLOCK = registerFluidBlock("liquid_estrogen", LIQUID_ESTROGEN.still(), true);
    public static FluidType FILTRATED_HORSE_URINE = register("filtrated_horse_urine", new FiltratedHorseUrineFluid.Still(), new FiltratedHorseUrineFluid.Flowing());
    public static Block FILTRATED_HORSE_URINE_BLOCK = registerFluidBlock("filtrated_horse_urine", FILTRATED_HORSE_URINE.still(), true);
    public static FluidType HORSE_URINE = register("horse_urine", new HorseUrineFluid.Still(), new HorseUrineFluid.Flowing());
    public static Block HORSE_URINE_BLOCK = registerFluidBlock("horse_urine", HORSE_URINE.still(), true);
    public static FluidType MOLTEN_AMETHYST = register("molten_amethyst", new MoltenAmethystFluid.Still(), new MoltenAmethystFluid.Flowing());
    public static Block MOLTEN_AMETHYST_BLOCK = registerFluidBlock("molten_amethyst", MOLTEN_AMETHYST.still(), false);

    public static void register() {}

    public static FluidType register(String id, FlowableFluid still, FlowableFluid flowing) {
        return new FluidType(
                Registry.register(Registry.FLUID, id(id), still),
                Registry.register(Registry.FLUID, id("flowing_" + id), flowing),
                EstrogenItems.register(id + "_bucket", new BucketItem(still, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(EstrogenItems.ESTROGEN_GROUP))
        ));
    }

    public static Block registerFluidBlock(String id, FlowableFluid fluid, boolean water) {
        return Registry.register(Registry.BLOCK, id(id), new FluidBlock(fluid, AbstractBlock.Settings.copy(water ? Blocks.WATER : Blocks.LAVA)));
    }

    public record FluidType(FlowableFluid still, FlowableFluid flowing, Item bucket) {}
}