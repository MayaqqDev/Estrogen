package dev.mayaqq.estrogen.forge.client;

import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.registry.client.EstrogenRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import dev.mayaqq.estrogen.client.EstrogenClient;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EstrogenForgeClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EstrogenClient.init();
    }

    @SubscribeEvent
    public static void modelRegistry(ModelEvent.RegisterAdditional event) {
        // Has to have this because if it doesn't, it breaks with cobblemon (for some reason)
        EstrogenRenderer.register();
    }
}
