package dev.mayaqq.estrogen.forge.client.menu;

import com.simibubi.create.infrastructure.gui.CreateMainMenuScreen;
import dev.mayaqq.estrogen.client.button.OpenEstrogenMenuButton;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class OpenEstrogenMenuForge {
    @SubscribeEvent
    public static void onGuiInit(ScreenEvent.Init event) {
        Screen gui = event.getScreen();
        if (gui instanceof CreateMainMenuScreen && EstrogenConfig.client().estrogenButtonEnabled.get()) {
            event.getListenersList()
                    .stream()
                    .filter(w -> w instanceof Button)
                    .map(w -> (Button) w)
                    .filter(w -> w.getMessage().getContents() instanceof TranslatableContents translatable && translatable.getKey().equals("create.menu.configure"))
                    .findFirst()
                    .ifPresent(w -> {
                        event.addListener(new OpenEstrogenMenuButton(w.getX() + EstrogenConfig.client().estrogenButtonXOffset.get(), w.getY() + EstrogenConfig.client().estrogenButtonYOffset.get()));
                    });
        }
    }
}
