package dev.mayaqq.estrogen.utils.registry;

import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import earth.terrarium.botarium.common.registry.fluid.FluidProperties;
import net.minecraft.world.level.material.Fluid;
import uwu.serenity.critter.api.AbstractBuilder;
import uwu.serenity.critter.api.AbstractRegistrar;
import uwu.serenity.critter.api.BuilderCallback;
import uwu.serenity.critter.api.entry.Delegate;
import uwu.serenity.critter.api.entry.RegistryEntry;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class FluidBuilder<F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> extends AbstractBuilder<Fluid, F, P, FluidBuilder<F, F2, P>> {

    private final Function<FluidData, F> sourceFactory;
    private final Function<FluidData, F2> flowingFactory;

    private Consumer<FluidProperties> properties;


    public FluidBuilder(String name, AbstractRegistrar<Fluid, ?> owner, P parent, BuilderCallback<Fluid, F> callback, Function<FluidData, F> sourceFactory, Function<FluidData, F2> flowingFactory) {
        super(name, owner, parent, callback);
        this.sourceFactory = sourceFactory;
        this.flowingFactory = flowingFactory;
    }

    @Override
    protected F createEntry() {
        return null;
    }

    @Override
    protected RegistryEntry<F> wrapDelegate(Delegate<F> holder) {
        return null;
    }
}
