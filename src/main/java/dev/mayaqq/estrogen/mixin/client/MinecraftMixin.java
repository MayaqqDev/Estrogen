package dev.mayaqq.estrogen.mixin.client;

import com.mojang.blaze3d.platform.Window;
import dev.mayaqq.cynosure.utils.fun.UwUfy;
import dev.mayaqq.estrogen.client.features.TextRendererFeatures;
import dev.mayaqq.estrogen.config.EstrogenClientConfig;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import dev.mayaqq.estrogen.content.EstrogenMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;blockPosition()Lnet/minecraft/core/BlockPos;", shift = At.Shift.AFTER), cancellable = true)
    private void getSituationalMusic(CallbackInfoReturnable<Music> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (EstrogenClientConfig.ambientMusic && player.hasEffect(EstrogenEffects.getEstrogen())) {
            cir.setReturnValue(EstrogenMusic.EstrogenAmbient);
        }
    }
}
