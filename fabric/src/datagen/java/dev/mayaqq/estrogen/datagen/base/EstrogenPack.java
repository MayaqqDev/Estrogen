package dev.mayaqq.estrogen.datagen.base;

import dev.mayaqq.estrogen.datagen.base.platform.recipes.PlatformRecipeHelper;
import dev.mayaqq.estrogen.datagen.mixins.PackInvoker;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class EstrogenPack {

    private final FabricDataGenerator.Pack pack;
    private final PlatformRecipeHelper helper;

    public EstrogenPack(FabricDataGenerator.Pack pack, PlatformRecipeHelper helper) {
        this.pack = pack;
        this.helper = helper;
    }

    public static EstrogenPack create(FabricDataGenerator fdg, String name, Path output, PlatformRecipeHelper helper) {
        ModContainer mod = FabricLoader.getInstance().getModContainer("estrogen").orElseThrow();
        return new EstrogenPack(
                PackInvoker.create(
                        fdg, true,
                        name,
                        new FabricDataOutput(mod, output, fdg.isStrictValidationEnabled())
                ),
                helper
        );
    }

    public <T extends DataProvider> void addProvider(FabricDataGenerator.Pack.Factory<T> factory) {
        pack.addProvider(factory);
    }

    public <T extends DataProvider> void addProvider(FabricDataGenerator.Pack.RegistryDependentFactory<T> factory) {
        pack.addProvider(factory);
    }

    public <T extends DataProvider> void addProvider(PlatformFactory<T> factory) {
        addProvider(output -> factory.create(output, helper));
    }

    public <T extends DataProvider> void addProvider(PlatformRegistryDependentFactory<T> factory) {
        addProvider((FabricDataGenerator.Pack.RegistryDependentFactory<T>) (output, registriesFuture) -> factory.create(output, registriesFuture, helper));
    }

    @FunctionalInterface
    public interface PlatformFactory<T extends DataProvider> {
        T create(FabricDataOutput output, PlatformRecipeHelper helper);
    }

    @FunctionalInterface
    public interface PlatformRegistryDependentFactory<T extends DataProvider> {
        T create(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, PlatformRecipeHelper helper);
    }


}
