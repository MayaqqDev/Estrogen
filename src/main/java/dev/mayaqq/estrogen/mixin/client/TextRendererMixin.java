package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.Dash;
import dev.mayaqq.estrogen.utils.UwUfy;
import dev.mayaqq.estrogen.utils.extensions.UwuOrderedText;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextRenderer.class)
public class TextRendererMixin {

    @ModifyVariable(
                method = "drawString(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;IIZ)I",
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
            method = "drawOrderedText(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I",
            at = @At(
                    value = "HEAD",
                    ordinal = 0
            ),
            argsOnly = true
    )
    private OrderedText modifyText(OrderedText text) {
        if (Dash.uwufy) return new UwuOrderedText(text); else return text;
    }
}
