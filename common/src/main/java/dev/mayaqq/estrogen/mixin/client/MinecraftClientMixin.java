package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.Dash;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo info) {
        Dash.dashClientTick();
    }
}
