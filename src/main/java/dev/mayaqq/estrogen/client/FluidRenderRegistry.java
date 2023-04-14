package dev.mayaqq.estrogen.client;

import dev.mayaqq.estrogen.registry.FluidRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.util.Identifier;

public class FluidRenderRegistry {
    public static void register() {
        FluidRenderHandlerRegistry.INSTANCE.register(FluidRegistry.STILL_MOLTEN_SLIME, FluidRegistry.FLOWING_MOLTEN_SLIME, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                144238144
        ));
        FluidRenderHandlerRegistry.INSTANCE.register(FluidRegistry.STILL_TESTOSTERONE_MIXTURE, FluidRegistry.FLOWING_TESTOSTERONE_MIXTURE, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                15414810
        ));
        FluidRenderHandlerRegistry.INSTANCE.register(FluidRegistry.STILL_LIQUID_ESTROGEN, FluidRegistry.FLOWING_LIQUID_ESTROGEN, new SimpleFluidRenderHandler(
                new Identifier("minecraft:block/water_still"),
                new Identifier("minecraft:block/water_flow"),
                104164161
        ));
    }
}