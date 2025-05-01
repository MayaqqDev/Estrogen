package dev.mayaqq.estrogen.compat.figura

import dev.mayaqq.estrogen.config.EstrogenClientConfig
import net.minecraft.world.entity.player.Player
import org.figuramc.figura.avatar.AvatarManager
import org.figuramc.figura.utils.RenderUtils

object FiguraCompat {
    @JvmStatic
    fun renderBoobs(player: Player): Boolean {
        if (EstrogenClientConfig.Compat.figura) {
            val avatar = AvatarManager.getAvatar(player)
            if (RenderUtils.vanillaModelAndScript(avatar)) {
                return avatar.luaRuntime.vanilla_model.BODY.checkVisible()
            }
        }
        return true
    }

    @JvmStatic
    fun renderBoobArmor(player: Player): Boolean {
        if (EstrogenClientConfig.Compat.figura) {
            val avatar = AvatarManager.getAvatar(player)
            if (RenderUtils.vanillaModelAndScript(avatar)) {
                return avatar.luaRuntime.vanilla_model.CHESTPLATE_BODY.checkVisible()
            }
        }
        return true
    }
}