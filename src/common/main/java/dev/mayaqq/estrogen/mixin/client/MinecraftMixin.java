package dev.mayaqq.estrogen.mixin.client;

import com.mojang.blaze3d.platform.Window;
import dev.mayaqq.cynosure.utils.fun.UwUfy;
import dev.mayaqq.estrogen.client.features.TextRendererFeatures;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow protected abstract String createTitle();

    @Shadow public abstract Window getWindow();

    @Inject(method = "updateTitle()V", at = @At("HEAD"), cancellable = true)
    private void updateTitle(final CallbackInfo info) {
        if(TextRendererFeatures.getUwufy()) {
            info.cancel();
            this.getWindow().setTitle(UwUfy.uwufy(this.createTitle()));
        }
    }
}
