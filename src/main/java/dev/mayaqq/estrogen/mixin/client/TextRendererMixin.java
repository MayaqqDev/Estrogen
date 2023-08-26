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
                method = "drawInternal(Ljava/lang/String;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZIIZ)I",
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
            method = "drawInternal(Lnet/minecraft/text/OrderedText;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I",
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
