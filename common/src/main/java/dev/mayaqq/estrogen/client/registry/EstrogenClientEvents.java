package dev.mayaqq.estrogen.client.registry;

import dev.mayaqq.estrogen.client.Dash;
import net.minecraft.client.Minecraft;

public class EstrogenClientEvents {
    public static void onDisconnect() {
        Dash.uwufy = false;
        Minecraft.getInstance().updateTitle();
    }
}
