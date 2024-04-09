package dev.mayaqq.estrogen.fabric.mixin;

import dev.mayaqq.estrogen.registry.EstrogenEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "die", at = @At("HEAD"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        EstrogenEvents.onEntityDeath((LivingEntity) (Object) this, damageSource);
    }
}
