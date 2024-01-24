package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.utils.UwUfy;
import dev.mayaqq.estrogen.utils.extensions.UwuOrderedText;
import net.minecraft.client.gui.Font;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Font.class)
public class TextRendererMixin {

    @ModifyVariable(
                method = "drawInternal(Ljava/lang/String;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZIIZ)I",
                at = @At(
                        value = "HEAD",
                        ordinal = 0
                ),
            argsOnly = true
        )
    private String modifyString(String text) {
        if (Dash.uwufy) return UwUfy.uwufyString(text); else return text;
    }

    @ModifyVariable(
            method = "drawInternal(Lnet/minecraft/util/FormattedCharSequence;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I",
            at = @At(
                    value = "HEAD",
                    ordinal = 0
            ),
            argsOnly = true
    )
    private FormattedCharSequence modifyText(FormattedCharSequence text) {
        if (Dash.uwufy) return new UwuOrderedText(text); else return text;
    }
}
