package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.serenity.nullbus.dispatch.internal.ConstantsKt;

@Mixin(value = ConstantsKt.class, remap = false)
public class NullbusConstantsMixin {

    //TODO: an actual solution in nullbus
    @ModifyReturnValue(
            method = "getInternalName",
            at = @At("RETURN")
    )
    private static String modifyInternalName(String original, @Local(argsOnly = true) Class<?> clazz) {
        String descriptor = clazz.descriptorString();
        if (descriptor.startsWith("L") && descriptor.endsWith(";")) {
            return descriptor.substring(1, descriptor.length() - 1);
        }
        return original;
    }
}
