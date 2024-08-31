package dev.mayaqq.estrogen.client.cosmetics.ui;

import com.simibubi.create.foundation.gui.ScreenOpener;
import dev.mayaqq.estrogen.client.cosmetics.service.CosmeticsApi;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CosmeticUI {
    public static final Component COSMETICS = Component.translatable("gui.estrogen.cosmetics");
    public static final Component LOGIN = Component.translatable("gui.estrogen.cosmetics.login");
    public static final Component PREVIEW_IN_GAME = Component.translatable("gui.estrogen.cosmetics.preview_in_game");

    public static void open(Screen parent) {
        if (CosmeticsApi.hasSessionToken()) {
            ScreenOpener.open(new CosmeticsScreen(parent));
        } else {
            ScreenOpener.open(new LoginScreen(parent));
        }
    }
}
