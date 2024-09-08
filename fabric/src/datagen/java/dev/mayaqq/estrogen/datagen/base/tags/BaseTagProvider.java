package dev.mayaqq.estrogen.datagen.base.tags;

import dev.mayaqq.estrogen.datagen.base.platform.ModPlatform;
import dev.mayaqq.estrogen.datagen.base.platform.PlatformHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class BaseTagProvider<T> extends FabricTagProvider<T> {

    private final PlatformHelper helper;

    public BaseTagProvider(
            FabricDataOutput output,
            ResourceKey<Registry<T>> registryKey,
            CompletableFuture<HolderLookup.Provider> registriesFuture,
            PlatformHelper helper
    ) {
        super(output, registryKey, registriesFuture);
        this.helper = helper;
    }

    @Override
    public @NotNull String getName() {
        return this.helper.getName("Estrogen's Tags for " + this.registryKey.location());
    }

    protected ModPlatform getPlatform() {
        return this.helper.getPlatform();
    }

    public static abstract class ItemProvider extends BaseTagProvider<Item> {

        public ItemProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, PlatformHelper helper) {
            super(output, Registries.ITEM, registriesFuture, helper);
        }

        @Override
        protected ResourceKey<Item> reverseLookup(Item element) {
            return element.builtInRegistryHolder().key();
        }
    }

    public static abstract class BlockProvider extends BaseTagProvider<Block> {

        public BlockProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, PlatformHelper helper) {
            super(output, Registries.BLOCK, registriesFuture, helper);
        }

        @Override
        protected ResourceKey<Block> reverseLookup(Block element) {
            return element.builtInRegistryHolder().key();
        }
    }

    public static abstract class FluidProvider extends BaseTagProvider<Fluid> {

        public FluidProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, PlatformHelper helper) {
            super(output, Registries.FLUID, registriesFuture, helper);
        }

        @Override
        protected ResourceKey<Fluid> reverseLookup(Fluid element) {
            return element.builtInRegistryHolder().key();
        }
    }

    public static abstract class EntityProvider extends BaseTagProvider<EntityType<?>> {

        public EntityProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, PlatformHelper helper) {
            super(output, Registries.ENTITY_TYPE, registriesFuture, helper);
        }

        @Override
        protected ResourceKey<EntityType<?>> reverseLookup(EntityType<?> element) {
            return element.builtInRegistryHolder().key();
        }
    }
}
