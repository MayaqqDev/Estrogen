package dev.mayaqq.estrogen.neoforge.mixins.client.compat.jei;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.mayaqq.estrogen.compat.recipeviewers.api.jei.JeiPluginRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.neoforge.startup.ForgePluginFinder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Pseudo
@Mixin(targets = "mezz.jei.neoforge.startup.ForgePluginFinder", priority = 900)
public class ForgePluginFinderMixin {
    @ModifyReturnValue(
            method = "getModPlugins",
            at = @At("RETURN")
    )
    private static List<IModPlugin> modifyReturn(List<IModPlugin> original) {
        var list = new ArrayList<IModPlugin>();
        list.addAll(original);
        list.addAll(JeiPluginRegister.INSTANCE.getPlugins());
        return list;
    }
}
