package dev.mayaqq.estrogen.forge.client;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.client.EstrogenClient;
import dev.mayaqq.estrogen.registry.client.EstrogenRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod.EventBusSubscriber(modid = Estrogen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EstrogenForgeClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // Config Button
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new BaseConfigScreen(screen, MOD_ID)));
        // Common Client Init
        EstrogenClient.init();
    }

    @SubscribeEvent
    public static void modelRegistry(ModelEvent.RegisterAdditional event) {
        // Has to have this because if it doesn't, it breaks with cobblemon (for some reason)
        EstrogenRenderer.register();
    }
}
