package dev.mayaqq.estrogen.utils.registry;

import dev.mayaqq.estrogen.platform.ClientPlatform;
import earth.terrarium.botarium.common.registry.fluid.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import uwu.serenity.critter.api.AbstractBuilder;
import uwu.serenity.critter.api.AbstractRegistrar;
import uwu.serenity.critter.api.BuilderCallback;
import uwu.serenity.critter.api.entry.Delegate;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.stdlib.blocks.BlockBuilder;
import uwu.serenity.critter.stdlib.blocks.BlockEntry;
import uwu.serenity.critter.stdlib.blocks.BlockRegistrar;
import uwu.serenity.critter.stdlib.items.ItemBuilder;
import uwu.serenity.critter.stdlib.items.ItemEntry;
import uwu.serenity.critter.stdlib.items.ItemRegistrar;
import uwu.serenity.critter.utils.environment.EnvExecutor;
import uwu.serenity.critter.utils.environment.Environment;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EstrogenFluidBuilder<F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid, P> extends AbstractBuilder<Fluid, F, P, EstrogenFluidBuilder<F, F2, P>> {

    private final Function<FluidData, F> sourceFactory;
    private final Function<FluidData, F2> flowingFactory;
    private final ResourceKey<Fluid> flowingKey;

    private Consumer<FluidProperties.Builder> properties;
    private RegistryEntry<F2> flowingWrapper;
    private FluidData builtProps;

    private BlockEntry<? extends BotariumLiquidBlock> blockEntry;
    private ItemEntry<? extends BucketItem> bucketEntry;


    public EstrogenFluidBuilder(String name, AbstractRegistrar<Fluid, ?> owner, P parent, BuilderCallback<Fluid, F> callback, Function<FluidData, F> sourceFactory, Function<FluidData, F2> flowingFactory) {
        super(name, owner, parent, callback);
        this.sourceFactory = sourceFactory;
        this.flowingFactory = flowingFactory;
        this.flowingKey = owner.createResourceKey(name + "_flowing");
        if(!(owner instanceof EstrogenFluidRegistrar))
            throw new IllegalStateException("Cannot create a fluid builder on a non-fluid registrar");
    }

    public EstrogenFluidBuilder<F, F2, P> properties(Consumer<FluidProperties.Builder> props) {
        if(this.properties == null) this.properties = props;
        else this.properties = this.properties.andThen(props);
        return this;
    }

    public EstrogenFluidBuilder<F, F2, P> renderType(Supplier<Supplier<RenderType>> renderType) {
        EnvExecutor.runWhenOn(Environment.CLIENT, () -> () -> this.onRegister(f -> {
            RenderType rt = renderType.get().get();
            ClientPlatform.fluidRenderLayerMap(f, rt);
            ClientPlatform.fluidRenderLayerMap(flowingWrapper.get(), rt);
        }));
        return this;
    }

    public <B extends BotariumLiquidBlock> BlockBuilder<B, EstrogenFluidBuilder<F, F2, P>> block(BiFunction<FluidData, BlockBehaviour.Properties, B> blockFactory) {
        BlockRegistrar blocks = owner.<Block, BlockRegistrar>child("liquidBlock", Registries.BLOCK, BlockRegistrar::new);
        return new BlockBuilder<>(this.name, blocks, this,
            (key1, builder, factory, entryFactory) -> {
                var blockEntry = (BlockEntry<B>) blocks.<B>getDefaultCallback().accept(key1, builder, factory, entryFactory);
                this.blockEntry = blockEntry;
                return blockEntry;
            },
            props -> blockFactory.apply(builtProps, props)
        );
    }

    public <I extends FluidBucketItem> ItemBuilder<I, EstrogenFluidBuilder<F, F2, P>> bucket(BiFunction<FluidData, Item.Properties, I> bucketFactory) {
        ItemRegistrar buckets = owner.<Item, ItemRegistrar>child("buckets", Registries.ITEM, ItemRegistrar::new);
        return new ItemBuilder<>(this.name + "_bucket", buckets, this,
            (key1, builder, factory, entryFatory) -> {
                var itemEntry = (ItemEntry<I>) buckets.<I>getDefaultCallback().accept(key1, builder, factory, entryFatory);
                this.bucketEntry = itemEntry;
                return itemEntry;
            },
            props -> bucketFactory.apply(builtProps, props)
        );
    }

    private void buildProperties() {
        FluidProperties.Builder builder = FluidProperties.create();
        if(properties != null) properties.accept(builder);
        this.builtProps = ((EstrogenFluidRegistrar) owner).dataRegistry.register(builder.build(key.location()));
    }

    private F2 createFlowingEntry() {
        return flowingFactory.apply(builtProps);
    }

    private RegistryEntry<F2> wrapFlowing(Delegate<F2> delegate) {
        return new RegistryEntry<>(flowingKey, delegate);
    }

    @Override
    protected F createEntry() {
        return sourceFactory.apply(builtProps);
    }

    @Override
    protected RegistryEntry<F> wrapDelegate(Delegate<F> holder) {
        return new EstrogenFluidEntry<>(key, holder, builtProps, flowingWrapper, blockEntry, bucketEntry);
    }

    @Override
    public EstrogenFluidEntry<F, F2> register() {
        buildProperties();
        flowingWrapper = owner.<F2>getDefaultCallback().accept(flowingKey, null, this::createFlowingEntry, this::wrapFlowing);
        return (EstrogenFluidEntry<F, F2>) super.register();
    }

    @Override
    public P build() {
        throw new UnsupportedOperationException("This builder does not support internalization");
    }
}
