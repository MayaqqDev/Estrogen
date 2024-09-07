package dev.mayaqq.estrogen.utils.registry;

import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import earth.terrarium.botarium.common.registry.fluid.FluidRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;
import uwu.serenity.critter.RegistryManager;
import uwu.serenity.critter.api.AbstractRegistrar;

import java.util.function.Function;
import java.util.function.Supplier;

public class EstrogenFluidRegistrar extends AbstractRegistrar<Fluid, EstrogenFluidBuilder<?, ?, EstrogenFluidRegistrar>> {

    final FluidRegistry dataRegistry;

    public EstrogenFluidRegistrar(RegistryManager manager, ResourceKey<? extends Registry<Fluid>> registry) {
        super(manager, registry);
        this.dataRegistry = new FluidRegistry(manager.getNamespace());
    }

    public static EstrogenFluidRegistrar create(String modid) {
        return new EstrogenFluidRegistrar(RegistryManager.getInstance(modid), Registries.FLUID);
    }

    public <F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid> EstrogenFluidBuilder<F, F2, EstrogenFluidRegistrar> entry(String name, Function<FluidData, F> sourceFactory, Function<FluidData, F2> flowingFactory) {
        return new EstrogenFluidBuilder<>(name, this, this, getDefaultCallback(), sourceFactory, flowingFactory);
    }

    @Override
    public <V extends Fluid> EstrogenFluidBuilder<?, ?, EstrogenFluidRegistrar> entry(String name, Supplier<V> factory) {
        throw new UnsupportedOperationException("This registrar doesn't support default entries");
    }

    @Override
    public void register() {
        dataRegistry.initialize();
        super.register();
    }
}
