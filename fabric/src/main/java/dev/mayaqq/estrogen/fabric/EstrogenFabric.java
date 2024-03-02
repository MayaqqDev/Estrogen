package dev.mayaqq.estrogen.fabric;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenFluids;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Map;

public class EstrogenFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Register and configure the config
        EstrogenConfig.register();
        for (Map.Entry<ModConfig.Type, ConfigBase> pair : EstrogenConfig.CONFIGS.entrySet())
            ForgeConfigRegistry.INSTANCE.register(Estrogen.MOD_ID, pair.getKey(), pair.getValue().specification);
        // init Estrogen main class
        Estrogen.init();
        // Register Fabric specific Events
        EstrogenFabricEvents.register();

        EstrogenFluids.FLUIDS.stream().forEach(fluid -> BlockRenderLayerMap.INSTANCE.putFluid(fluid.get(), RenderType.translucent()));

        ModConfigEvents.loading(Estrogen.MOD_ID).register(EstrogenConfig::onLoad);
        ModConfigEvents.reloading(Estrogen.MOD_ID).register(EstrogenConfig::onReload);
    }
}