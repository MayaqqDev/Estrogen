package dev.mayaqq.estrogen.utils.registry;

import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import earth.terrarium.botarium.common.registry.fluid.FluidData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import uwu.serenity.critter.api.entry.Delegate;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.stdlib.blocks.BlockEntry;
import uwu.serenity.critter.stdlib.items.ItemEntry;

public class EstrogenFluidEntry<F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid> extends RegistryEntry<F> {

    private final FluidData properties;
    private final RegistryEntry<F2> flowing;
    private final BlockEntry<? extends BotariumLiquidBlock> block;
    private final ItemEntry<? extends BucketItem> bucket;

    public EstrogenFluidEntry(ResourceKey<? super F> key, Delegate<F> holder, FluidData properties, RegistryEntry<F2> flowing, BlockEntry<? extends BotariumLiquidBlock> block, ItemEntry<? extends BucketItem> bucket) {
        super(key, holder);
        this.properties = properties;
        this.flowing = flowing;
        this.block = block;
        this.bucket = bucket;
    }

    public FluidData getProperties() {
        return properties;
    }

    public RegistryEntry<F2> getFlowingEntry() {
        return flowing;
    }

    public BlockEntry<? extends BotariumLiquidBlock> getBlockEntry() {
        return block;
    }

    public ItemEntry<? extends BucketItem> getBucketEntry() {
        return bucket;
    }

    public F2 getFlowing() {
        return flowing.get();
    }

    public BotariumLiquidBlock getBlock() {
        return block.get();
    }

    public BucketItem getBucket() {
        return bucket.get();
    }
}
