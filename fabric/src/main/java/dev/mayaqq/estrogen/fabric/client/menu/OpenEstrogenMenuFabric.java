package dev.mayaqq.estrogen.fabric.client.menu;

import com.simibubi.create.infrastructure.gui.CreateMainMenuScreen;
import dev.mayaqq.estrogen.client.button.OpenEstrogenMenuButton;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.contents.TranslatableContents;

public class OpenEstrogenMenuFabric {
    public static void onGuiInit(Minecraft client, Screen gui, int scaledWidth, int scaledHeight) {
        if (gui instanceof CreateMainMenuScreen && EstrogenConfig.client().estrogenButtonEnabled.get()) {
            Screens.getButtons(gui).stream()
                    .filter(w -> w.getMessage().getContents() instanceof TranslatableContents translatable && translatable.getKey().equals("create.menu.configure")).findFirst()
                    .ifPresent(w -> {
                        gui.addRenderableWidget(new OpenEstrogenMenuButton(w.getX() + EstrogenConfig.client().estrogenButtonXOffset.get(), w.getY() + EstrogenConfig.client().estrogenButtonYOffset.get()));
                    });
        }
    }
}
