package dev.mayaqq.estrogen.forge.client;

import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import dev.mayaqq.estrogen.Estrogen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import dev.mayaqq.estrogen.client.EstrogenClient;

import static dev.mayaqq.estrogen.Estrogen.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EstrogenForgeClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        // Config Button
        ModLoadingContext.get().getActiveContainer().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new BaseConfigScreen(screen, MOD_ID)));
        EstrogenClient.init();
    }

    // Curios
    @SubscribeEvent
    public static void textureStitch(TextureStitchEvent.Pre event) {
        event.addSprite(Estrogen.id("slot/thighs"));
    }
}
