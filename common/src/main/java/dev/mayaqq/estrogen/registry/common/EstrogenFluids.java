package dev.mayaqq.estrogen.registry.common;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;

public class EstrogenFluids {

    public static Registrar<Fluid> FLUIDS = Estrogen.MANAGER.get().get(Registry.FLUID);

    public static RegistrySupplier<ArchitecturyFlowingFluid.Source> MOLTEN_SLIME = FLUIDS.register(Estrogen.id("molten_slime"), () -> new ArchitecturyFlowingFluid.Source(EstrogenFluidAttributes.MOLTEN_SLIME));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Source> TESTOSTERONE_MIXTURE = FLUIDS.register(Estrogen.id("testosterone_mixture"), () -> new ArchitecturyFlowingFluid.Source(EstrogenFluidAttributes.TESTOSTERONE_MIXTURE));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Source> LIQUID_ESTROGEN = FLUIDS.register(Estrogen.id("liquid_estrogen"), () -> new ArchitecturyFlowingFluid.Source(EstrogenFluidAttributes.LIQUID_ESTROGEN));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Source> FILTRATED_HORSE_URINE = FLUIDS.register(Estrogen.id("filtrated_horse_urine"), () -> new ArchitecturyFlowingFluid.Source(EstrogenFluidAttributes.FILTRATED_HORSE_URINE));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Source> HORSE_URINE = FLUIDS.register(Estrogen.id("horse_urine"), () -> new ArchitecturyFlowingFluid.Source(EstrogenFluidAttributes.HORSE_URINE));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Source> MOLTEN_AMETHYST = FLUIDS.register(Estrogen.id("molten_amethyst"), () -> new ArchitecturyFlowingFluid.Source(EstrogenFluidAttributes.MOLTEN_AMETHYST));

    public static RegistrySupplier<ArchitecturyFlowingFluid.Flowing> MOLTEN_SLIME_FLOWING = FLUIDS.register(Estrogen.id("flowing_molten_slime"), () -> new ArchitecturyFlowingFluid.Flowing(EstrogenFluidAttributes.MOLTEN_SLIME));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Flowing> TESTOSTERONE_MIXTURE_FLOWING = FLUIDS.register(Estrogen.id("flowing_testosterone_mixture"), () -> new ArchitecturyFlowingFluid.Flowing(EstrogenFluidAttributes.TESTOSTERONE_MIXTURE));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Flowing> LIQUID_ESTROGEN_FLOWING = FLUIDS.register(Estrogen.id("flowing_liquid_estrogen"), () -> new ArchitecturyFlowingFluid.Flowing(EstrogenFluidAttributes.LIQUID_ESTROGEN));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Flowing> FILTRATED_HORSE_URINE_FLOWING = FLUIDS.register(Estrogen.id("flowing_filtrated_horse_urine"), () -> new ArchitecturyFlowingFluid.Flowing(EstrogenFluidAttributes.FILTRATED_HORSE_URINE));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Flowing> HORSE_URINE_FLOWING = FLUIDS.register(Estrogen.id("flowing_horse_urine"), () -> new ArchitecturyFlowingFluid.Flowing(EstrogenFluidAttributes.HORSE_URINE));
    public static RegistrySupplier<ArchitecturyFlowingFluid.Flowing> MOLTEN_AMETHYST_FLOWING = FLUIDS.register(Estrogen.id("flowing_molten_amethyst"), () -> new ArchitecturyFlowingFluid.Flowing(EstrogenFluidAttributes.MOLTEN_AMETHYST));

    public static void register() {}
}