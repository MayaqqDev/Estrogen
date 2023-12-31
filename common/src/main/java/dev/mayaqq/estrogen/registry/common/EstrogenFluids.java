package dev.mayaqq.estrogen.registry.common;

import com.simibubi.create.AllFluids;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.registry.registries.Registrar;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.common.fluids.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import static dev.mayaqq.estrogen.Estrogen.REGISTRATE;
import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenFluids {

    public static Registrar<Fluid> FLUIDS = Estrogen.MANAGER.get().get(Registries.FLUID);
    public static Registrar<Item> FLUID_ITEMS = Estrogen.MANAGER.get().get(Registries.ITEM);

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

    public static FluidType register(String id, FlowingFluid still, FlowingFluid flowing) {
        return new FluidType(
                FLUIDS.register(id, () -> new ArchitecturyFlowingFluid.Source(EXAMPLE_FLUID_ATTRIBUTES)),
                FLUIDS.register("flowing_" + id, () -> new ArchitecturyFlowingFluid.Flowing(EXAMPLE_FLUID_ATTRIBUTES))
                FLUID_ITEMS.register(id + "_bucket", () -> new ArchitecturyBucketItem(still, new Item.Properties().arch$tab(Estrogen.ESTROGEN_GROUP.get())))
        );
        AllFluids
    }

    public static Block registerFluidBlock(String id, FlowingFluid fluid, boolean water) {
        return Registry.register(BuiltInRegistries.BLOCK, id(id), new LiquidBlock(fluid, BlockBehaviour.Properties.copy(water ? Blocks.WATER : Blocks.LAVA)));
    }

    public record FluidType(FlowingFluid still, FlowingFluid flowing, Item bucket) {}
}