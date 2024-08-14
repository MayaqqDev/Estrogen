package dev.mayaqq.estrogen.platform.forge;

import net.minecraft.world.entity.ai.attributes.Attribute;
import top.theillusivec4.caelus.api.CaelusApi;

public class ForgeHookImpl {
    public static Attribute getCaelusAttribute() {
        return CaelusApi.getInstance().getFlightAttribute();
    }
}
