package dev.mayaqq.estrogen.fabric.mixin;

import com.jozufozu.flywheel.Flywheel;
import com.jozufozu.flywheel.backend.gl.shader.GlShader;
import com.jozufozu.flywheel.backend.gl.shader.ShaderType;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GlShader.class)
public class DevGlShaderLogMixin {

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/lwjgl/opengl/GL20;glGetShaderi(II)I",
                    shift = At.Shift.BEFORE
            )
    )
    public void logImmediate(ResourceLocation name, ShaderType type, String source, CallbackInfo ci, @Local(ordinal = 1) String log) {
        Flywheel.LOGGER.info(log);
    }

}
