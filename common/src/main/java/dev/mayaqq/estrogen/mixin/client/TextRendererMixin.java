package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.features.UwUfy;
import dev.mayaqq.estrogen.utils.extensions.UwuOrderedText;
import net.minecraft.client.gui.Font;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Font.class)
public class TextRendererMixin {
    @ModifyVariable(
            method = "drawInternal(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I",
            at = @At(
                    value = "HEAD",
                    ordinal = 0
            ),
            argsOnly = true
    )
    private String modifyString(String text) {
        if (UwUfy.isEnabled()) return UwUfy.uwufyString(text);
        else return text;
    }

    @ModifyVariable(
            method = "drawInternal(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I",
            at = @At(
                    value = "HEAD",
                    ordinal = 0
            ),
            argsOnly = true
    )
    private FormattedCharSequence modifyText(FormattedCharSequence text) {
        if (UwUfy.isEnabled()) return new UwuOrderedText(text);
        else return text;
    }
}
