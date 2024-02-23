package dev.mayaqq.estrogen.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.mayaqq.estrogen.Estrogen;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;

public class EstrogenFluids {

    public static final ResourcefulRegistry<Fluid> FLUIDS = ResourcefulRegistries.create(BuiltInRegistries.FLUID, Estrogen.MOD_ID);

    public static final RegistryEntry<Fluid> MOLTEN_SLIME = FLUIDS.register("molten_slime", () -> new BotariumSourceFluid(EstrogenFluidProperties.MOLTEN_SLIME));
    public static final RegistryEntry<Fluid> TESTOSTERONE_MIXTURE = FLUIDS.register("testosterone_mixture", () -> new BotariumSourceFluid(EstrogenFluidProperties.TESTOSTERONE_MIXTURE));
    public static final RegistryEntry<Fluid> LIQUID_ESTROGEN = FLUIDS.register("liquid_estrogen", () -> new BotariumSourceFluid(EstrogenFluidProperties.LIQUID_ESTROGEN));
    public static final RegistryEntry<Fluid> FILTRATED_HORSE_URINE = FLUIDS.register("filtrated_horse_urine", () -> new BotariumSourceFluid(EstrogenFluidProperties.FILTRATED_HORSE_URINE));
    public static final RegistryEntry<Fluid> HORSE_URINE = FLUIDS.register("horse_urine", () -> new BotariumSourceFluid(EstrogenFluidProperties.HORSE_URINE));
    public static final RegistryEntry<Fluid> MOLTEN_AMETHYST = FLUIDS.register("molten_amethyst", () -> new BotariumSourceFluid(EstrogenFluidProperties.MOLTEN_AMETHYST));

    public static final RegistryEntry<Fluid> MOLTEN_SLIME_FLOWING = FLUIDS.register("flowing_molten_slime", () -> new BotariumFlowingFluid(EstrogenFluidProperties.MOLTEN_SLIME));
    public static final RegistryEntry<Fluid> TESTOSTERONE_MIXTURE_FLOWING = FLUIDS.register("flowing_testosterone_mixture", () -> new BotariumFlowingFluid(EstrogenFluidProperties.TESTOSTERONE_MIXTURE));
    public static final RegistryEntry<Fluid> LIQUID_ESTROGEN_FLOWING = FLUIDS.register("flowing_liquid_estrogen", () -> new BotariumFlowingFluid(EstrogenFluidProperties.LIQUID_ESTROGEN));
    public static final RegistryEntry<Fluid> FILTRATED_HORSE_URINE_FLOWING = FLUIDS.register("flowing_filtrated_horse_urine", () -> new BotariumFlowingFluid(EstrogenFluidProperties.FILTRATED_HORSE_URINE));
    public static final RegistryEntry<Fluid> HORSE_URINE_FLOWING = FLUIDS.register("flowing_horse_urine", () -> new BotariumFlowingFluid(EstrogenFluidProperties.HORSE_URINE));
    public static final RegistryEntry<Fluid> MOLTEN_AMETHYST_FLOWING = FLUIDS.register("flowing_molten_amethyst", () -> new BotariumFlowingFluid(EstrogenFluidProperties.MOLTEN_AMETHYST));
}