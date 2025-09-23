package dev.mayaqq.estrogen.mixin.client.compat.emi;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.emi.emi.platform.EmiAgnos;
import dev.emi.emi.registry.EmiPluginContainer;
import dev.mayaqq.estrogen.compat.recipeviewers.api.emi.EmiPluginRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(EmiAgnos.class)
public class EmiAgnosMixin {
    @ModifyReturnValue(
            method = "getPlugins",
            at = @At("RETURN")
    )
    private static List<EmiPluginContainer> modifyReturn(List<EmiPluginContainer> original) {
        var list = new ArrayList<EmiPluginContainer>();
        list.addAll(original);
        list.addAll(EmiPluginRegister.INSTANCE.getPlugins());
        return list;
    }
}
