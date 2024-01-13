package dev.mayaqq.estrogen.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClient;
import dev.mayaqq.estrogen.registry.common.EstrogenFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod(MOD_ID)
public class EstrogenForge {
    public EstrogenForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Submit our event bus to let architectury register our content on the right time
        bus.addListener(this::clientSetup);
        EventBuses.registerModEventBus(MOD_ID, bus);
        Estrogen.REGISTRATE.registerEventListeners(bus);
        Estrogen.init();
    }

    public void clientSetup(FMLClientSetupEvent event) {
        EstrogenClient.init();
    }
}