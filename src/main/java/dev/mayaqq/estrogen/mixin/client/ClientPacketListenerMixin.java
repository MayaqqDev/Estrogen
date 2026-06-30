package dev.mayaqq.estrogen.mixin.client;

import dev.mayaqq.estrogen.client.content.sounds.MothFlyingSoundInstance;
import dev.mayaqq.estrogen.content.entities.MothEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    /**
     * This mixin adds a MothFlyingSoundInstance to the sound manager when a moth entity is added.
     */
    @Inject(method = "postAddEntitySoundInstance", at = @At("HEAD"))
    private void postAddEntitySoundInstanceMixin(Entity entity, CallbackInfo ci) {
        if (entity instanceof MothEntity moth) {
            Minecraft.getInstance().getSoundManager().queueTickingSound(new MothFlyingSoundInstance(moth));
        }
    }
}
