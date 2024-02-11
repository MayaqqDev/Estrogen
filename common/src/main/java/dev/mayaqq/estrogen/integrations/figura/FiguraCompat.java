package dev.mayaqq.estrogen.integrations.figura;

import dev.mayaqq.estrogen.config.EstrogenConfig;
import net.minecraft.client.player.LocalPlayer;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.utils.RenderUtils;

public class FiguraCompat {
    public static boolean renderBoobs(LocalPlayer player) {
        if (EstrogenConfig.client().figura.get()) {
            Avatar avatar = AvatarManager.getAvatar(player);
            if (RenderUtils.vanillaModelAndScript(avatar)) {
                return avatar.luaRuntime.vanilla_model.BODY.checkVisible();
            }
        }
        return true;
    }

    public static boolean renderBoobArmor(LocalPlayer player) {
        if (EstrogenConfig.client().figura.get()) {
            Avatar avatar = AvatarManager.getAvatar(player);
            if (RenderUtils.vanillaModelAndScript(avatar)) {
                return avatar.luaRuntime.vanilla_model.CHESTPLATE_BODY.checkVisible();
            }
        }
        return true;
    }
}