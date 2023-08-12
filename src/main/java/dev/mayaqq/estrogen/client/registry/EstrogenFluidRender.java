package dev.mayaqq.estrogen.client.registry;

import dev.mayaqq.estrogen.registry.EstrogenFluids;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class EstrogenFluidRender {
    public static void register() {
        register(EstrogenFluids.MOLTEN_SLIME, 144238144, false);
        register(EstrogenFluids.TESTOSTERONE_MIXTURE, 154148010, true);
        register(EstrogenFluids.LIQUID_ESTROGEN, 104164161, true);
        register(EstrogenFluids.HORSE_URINE, 0x8C8B05, true);
        register(EstrogenFluids.FILTRATED_HORSE_URINE, 0xE1E114, true);
        register(EstrogenFluids.MOLTEN_AMETHYST, 0xAE7AFD, false);
    }

    public static void register(EstrogenFluids.FluidType fluidType, int tint, boolean translucent, String texture) {
        FluidRenderHandlerRegistry.INSTANCE.register(fluidType.still(), fluidType.flowing(), new SimpleFluidRenderHandler(
                new Identifier(texture + "still"),
                new Identifier(texture + "flow"),
                tint
        ));
        if (translucent) {
            BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), fluidType.still(), fluidType.flowing());
        }
    }

    public static void register(EstrogenFluids.FluidType fluidType, int tint, boolean translucent) {
        register(fluidType, tint, translucent, "minecraft:block/water_");
    }
}