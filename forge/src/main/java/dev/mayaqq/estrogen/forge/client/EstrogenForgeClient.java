package dev.mayaqq.estrogen.forge.client;

import dev.mayaqq.estrogen.Estrogen;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import dev.mayaqq.estrogen.client.EstrogenClient;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID)
public class EstrogenForgeClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        EstrogenClient.init();
    }
}
