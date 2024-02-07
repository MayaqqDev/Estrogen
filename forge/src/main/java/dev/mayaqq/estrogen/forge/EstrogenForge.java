package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.architectury.platform.forge.EventBuses;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.client.EstrogenRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Map;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {
    public EstrogenForge() {
        // Config
        EstrogenConfig.register();
        for (Map.Entry<ModConfig.Type, ConfigBase> pair : EstrogenConfig.CONFIGS.entrySet())
            ModLoadingContext.get().registerConfig(pair.getKey(), pair.getValue().specification);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Forge ugh
        EventBuses.registerModEventBus(MOD_ID, bus);

        // REGISTRATE registering
        Estrogen.REGISTRATE.registerEventListeners(bus);

        // Init Estrogen main class
        Estrogen.init();

        if(FMLEnvironment.dist == Dist.CLIENT) {
            EstrogenRenderer.register();
        }
    }
}