package dev.mayaqq.estrogen.client;

import earth.terrarium.botarium.util.CommonHooks;
import net.irisshaders.iris.api.v0.IrisApi;

import java.util.function.BooleanSupplier;

public class ShaderHelper {

    private static BooleanSupplier shaderPackInUse;

    public static boolean isShaderPackInUse() {
        return shaderPackInUse.getAsBoolean();
    }

    public static void init() {
        if(CommonHooks.isModLoaded("iris") || CommonHooks.isModLoaded("oculus")) {
            shaderPackInUse = () -> IrisApi.getInstance().isShaderPackInUse();
        } else {
            // I should probably account for canvas but it probably works there anyway so eh
            shaderPackInUse = () -> false;
        }
    }

}
