package dev.mayaqq.estrogen.fabric.mixin;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FabricDataGenerator.Pack.class)
public interface PackInvoker {
    @Invoker(value = "<init>", remap = false)
    static FabricDataGenerator.Pack create(FabricDataGenerator fdg, boolean shouldRun, String name, FabricDataOutput output) {
        throw new AssertionError();
    }
}
