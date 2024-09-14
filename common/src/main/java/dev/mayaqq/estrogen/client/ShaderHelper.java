package dev.mayaqq.estrogen.client;

import earth.terrarium.botarium.util.CommonHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.irisshaders.iris.api.v0.IrisApi;

import java.lang.reflect.Field;
import java.util.function.BooleanSupplier;

@Environment(EnvType.CLIENT)
public class ShaderHelper {

    private static BooleanSupplier shaderPackInUse;

    public static boolean isShaderPackInUse() {
        if(shaderPackInUse == null) init();
        return shaderPackInUse.getAsBoolean();
    }

    public static void init() {
        if (CommonHooks.isModLoaded("iris") || CommonHooks.isModLoaded("oculus")) {
            shaderPackInUse = () -> IrisApi.getInstance().isShaderPackInUse();
        } else if (Package.getPackage("net.optifine") != null) {
            shaderPackInUse = optifineShadersInUse();
        } else {
            shaderPackInUse = () -> false;
        }
    }

    private static BooleanSupplier optifineShadersInUse() {
        try {
            Class<?> ofShaders = Class.forName("net.optifine.shaders.Shaders");
            Field field = ofShaders.getDeclaredField("shaderPackLoaded");
            field.setAccessible(true);
            return () -> {
                try {
                    return field.getBoolean(null);
                } catch (IllegalAccessException ignored) {
                    return false;
                }
            };
        } catch (Exception ignored) {
            return () -> false;
        }
    }

}
