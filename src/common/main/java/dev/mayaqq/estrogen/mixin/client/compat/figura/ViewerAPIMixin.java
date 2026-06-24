package dev.mayaqq.estrogen.mixin.client.compat.figura;

import dev.mayaqq.estrogen.client.features.dash.ClientDash;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.entity.ViewerAPI;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ViewerAPI.class)
public class ViewerAPIMixin {
    @LuaWhitelist
    @LuaMethodDoc("host.on_dash_cooldown")
    public boolean onDashCooldown() {
        return ClientDash.INSTANCE.isOnCooldown();
    }
}
