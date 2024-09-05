package dev.mayaqq.estrogen.utils.registry;

import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import uwu.serenity.critter.api.AbstractRegistrar;
import uwu.serenity.critter.api.entry.Delegate;
import uwu.serenity.critter.api.entry.RegistryEntry;
import uwu.serenity.critter.stdlib.blocks.BlockEntry;
import uwu.serenity.critter.stdlib.items.ItemEntry;

public class FluidEntry<F extends BotariumSourceFluid, F2 extends BotariumFlowingFluid> extends RegistryEntry<F> {

    private final RegistryEntry<F2> flowing;
    private final BlockEntry<? extends BotariumLiquidBlock> block;
    private final ItemEntry<? extends BucketItem> bucket;

    public FluidEntry(ResourceKey<? super F> key, Delegate<F> holder, RegistryEntry<F2> flowing, BlockEntry<? extends BotariumLiquidBlock> block, ItemEntry<? extends BucketItem> bucket) {
        super(key, holder);
        this.flowing = flowing;
        this.block = block;
        this.bucket = bucket;
    }
}
