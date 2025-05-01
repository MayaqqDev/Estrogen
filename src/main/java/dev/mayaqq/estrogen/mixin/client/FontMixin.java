package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.cynosure.utils.fun.UwUOrderedText;
import dev.mayaqq.cynosure.utils.fun.UwUfyKt;
import dev.mayaqq.estrogen.client.features.TextRendererFeatures;
import net.minecraft.client.gui.Font;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Font.class)
public class FontMixin {
    @ModifyVariable(
            method = "drawInternal(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I",
            at = @At(
                    value = "HEAD",
                    ordinal = 0
            ),
            argsOnly = true
    )
    private String modifyString(String text) {
        if (TextRendererFeatures.getUwufy()) return UwUfyKt.uwufy(text);
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
        //TODO: Check if this works, kotlin in java is weird and pain and I dont like it
        if (TextRendererFeatures.getUwufy()) return UwUOrderedText.orderedToUwUText(text);
        else return text;
    }
}
