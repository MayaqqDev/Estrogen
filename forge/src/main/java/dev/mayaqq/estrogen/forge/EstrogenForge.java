package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import dev.architectury.platform.forge.EventBuses;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClient;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {
    public EstrogenForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Submit our event bus to let architectury register our content on the right time
        bus.addListener(this::clientSetup);
        EventBuses.registerModEventBus(MOD_ID, bus);
        Estrogen.REGISTRATE.registerEventListeners(bus);

        EstrogenConfig.register();

        for (Map.Entry<ModConfig.Type, ConfigBase> pair : EstrogenConfig.CONFIGS.entrySet())
            ModLoadingContext.get().registerConfig(pair.getKey(), pair.getValue().specification);

        Estrogen.init();
    }

    public void clientSetup(FMLClientSetupEvent event) {
        EstrogenClient.init();
    }
}