package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.cosmetics.model.animation.Animations;
import dev.mayaqq.estrogen.client.features.UwUfy;
import dev.mayaqq.estrogen.client.features.boobs.BoobPhysicsManager;
import dev.mayaqq.estrogen.client.features.dash.DreamBlockEffect;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.DreamBlockTexture;
import dev.mayaqq.estrogen.client.registry.blockRenderers.dreamBlock.texture.advanced.DynamicDreamTexture;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import dev.mayaqq.estrogen.registry.EstrogenMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo info) {
        BoobPhysicsManager.tick();
        DreamBlockEffect.tick();
        DreamBlockTexture.animationTick();
        DynamicDreamTexture.INSTANCE.tick();
        DynamicDreamTexture.resetActive();
        UwUfy.tick();
        Animations.tick();
    }

    @Inject(method = "updateTitle()V", at = @At("HEAD"), cancellable = true)
    private void updateTitle(final CallbackInfo info) {
        if(UwUfy.isEnabled()) {
            info.cancel();
            Minecraft instance = Minecraft.getInstance();
            instance.getWindow().setTitle(UwUfy.uwufyString(instance.createTitle()));
        }
    }

    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;blockPosition()Lnet/minecraft/core/BlockPos;", shift = At.Shift.AFTER), cancellable = true)
    private void getSituationalMusic(CallbackInfoReturnable<Music> cir) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (EstrogenConfig.client().ambientMusic.get() && player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get())) {
            cir.setReturnValue(EstrogenMusic.ESTROGEN_AMBIENT);
        }
    }
}
