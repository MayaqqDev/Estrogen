package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.Dash;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void render(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        Dash.renderOverlayTick(guiGraphics);
    }
}
