package dev.mayaqq.estrogen.registry;

import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EstrogenFluidRender {
    public static void register() {
        registerLava(EstrogenFluids.MOLTEN_SLIME, 144238144, false);
        registerLava(EstrogenFluids.MOLTEN_AMETHYST, 0xAE7AFD, false);
        registerWater(EstrogenFluids.TESTOSTERONE_MIXTURE, 154148010, true);
        registerWater(EstrogenFluids.LIQUID_ESTROGEN, 104164161, true);
        registerWater(EstrogenFluids.HORSE_URINE, 0x8C8B05, true);
        registerWater(EstrogenFluids.FILTRATED_HORSE_URINE, 0xE1E114, true);
    }
    //IClientFluidTypeExtensions on forge? closest thing i could find
    public static void register(EstrogenFluids.FluidType fluidType, int tint, boolean translucent, String texture) {
        FluidRenderHandlerRegistry.INSTANCE.register(fluidType.still(), fluidType.flowing(), new SimpleFluidRenderHandler (
                new ResourceLocation(texture + "still"),
                new ResourceLocation(texture + "flow"),
                tint
        ));
        if (translucent) {
            BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(), fluidType.still(), fluidType.flowing());
        }
    }

    public static void registerWater(EstrogenFluids.FluidType fluidType, int tint, boolean translucent) {
        register(fluidType, tint, translucent, "minecraft:block/water_");
    }

    public static void registerLava(EstrogenFluids.FluidType fluidType, int tint, boolean translucent) {
        register(fluidType, tint, translucent, "estrogen:block/blank_lava/blank_lava_");
    }
}