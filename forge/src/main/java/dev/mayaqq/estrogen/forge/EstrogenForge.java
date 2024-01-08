package dev.mayaqq.estrogen.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {
    public EstrogenForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(EstrogenForgeEvents.class);
        EventBuses.registerModEventBus(MOD_ID, bus);
        Estrogen.REGISTRATE.registerEventListeners(bus);
        Estrogen.init();
    }
}