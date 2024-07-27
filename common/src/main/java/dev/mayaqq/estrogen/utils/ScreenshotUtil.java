package dev.mayaqq.estrogen.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;

public class ScreenshotUtil {
    @Environment(EnvType.CLIENT)
    public static void takeScreenshot() {
        Minecraft mc = Minecraft.getInstance();
        Screenshot.grab(mc.gameDirectory, mc.getMainRenderTarget(), component -> mc.execute(() -> mc.gui.getChat().addMessage(component)));
    }
}
