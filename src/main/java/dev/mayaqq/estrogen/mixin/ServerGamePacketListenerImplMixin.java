package dev.mayaqq.estrogen.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.mayaqq.estrogen.content.EstrogenEffects;
import dev.mayaqq.estrogen.utils.StupidUtilsKt;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @ModifyExpressionValue(
            method = "handleMovePlayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;isChangingDimension()Z"
            )
    )
    private boolean disableWhenOnEstrogen(boolean original) {
        if (player.hasEffect(StupidUtilsKt.holder(EstrogenEffects.getEstrogen()))) return true;
        return original;
    }
}

