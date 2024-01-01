package dev.mayaqq.estrogen.registry.common;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class EstrogenFluids {

    public static Registrar<Fluid> FLUIDS = Estrogen.MANAGER.get().get(Registries.FLUID);

    public static FluidType MOLTEN_SLIME = register("molten_slime", EstrogenFluidAttributes.MOLTEN_SLIME);
    public static FluidType TESTOSTERONE_MIXTURE = register("testosterone_mixture", EstrogenFluidAttributes.TESTOSTERONE_MIXTURE);
    public static FluidType LIQUID_ESTROGEN = register("liquid_estrogen", EstrogenFluidAttributes.LIQUID_ESTROGEN);
    public static FluidType FILTRATED_HORSE_URINE = register("filtrated_horse_urine", EstrogenFluidAttributes.FILTRATED_HORSE_URINE);
    public static FluidType HORSE_URINE = register("horse_urine", EstrogenFluidAttributes.HORSE_URINE);
    public static FluidType MOLTEN_AMETHYST = register("molten_amethyst", EstrogenFluidAttributes.MOLTEN_AMETHYST);

    public static void register() {}

    public static FluidType register(String id, SimpleArchitecturyFluidAttributes attributes) {
        return new FluidType(
                FLUIDS.register(Estrogen.id(id), () -> new ArchitecturyFlowingFluid.Source(attributes)),
                FLUIDS.register(Estrogen.id("flowing_" + id), () -> new ArchitecturyFlowingFluid.Flowing(attributes))
        );
    }

    public record FluidType(RegistrySupplier<FlowingFluid> still, RegistrySupplier<FlowingFluid> flowing) {}
}