package dev.mayaqq.estrogen.forge;

import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import dev.architectury.platform.forge.EventBuses;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {
    public EstrogenForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Forge ugh
        EventBuses.registerModEventBus(MOD_ID, bus);
        // REGISTRATE registering
        Estrogen.REGISTRATE.registerEventListeners(bus);

        // Config
        EstrogenConfig.register();
        for (Map.Entry<ModConfig.Type, ConfigBase> pair : EstrogenConfig.CONFIGS.entrySet())
            ModLoadingContext.get().registerConfig(pair.getKey(), pair.getValue().specification);

        // Config Button
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new BaseConfigScreen(screen, MOD_ID)));

        // Init Estrogen main class
        Estrogen.init();
    }
}