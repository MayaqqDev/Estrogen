package dev.mayaqq.estrogen.integrations.figura;

import net.minecraft.client.player.LocalPlayer;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.utils.RenderUtils;

public class FiguraCompat {
    public static boolean renderBoobs(LocalPlayer player) {
        Avatar avatar = AvatarManager.getAvatar(player);
        return RenderUtils.vanillaModelAndScript(avatar) && avatar.luaRuntime.vanilla_model.BODY.checkVisible();
    }

    public static boolean renderBoobArmor(LocalPlayer player) {
        Avatar avatar = AvatarManager.getAvatar(player);
        return RenderUtils.vanillaModelAndScript(avatar) && avatar.luaRuntime.vanilla_model.CHESTPLATE_BODY.checkVisible();
    }
}