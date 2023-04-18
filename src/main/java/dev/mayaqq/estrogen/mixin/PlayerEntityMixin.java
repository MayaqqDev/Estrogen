package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.registry.EffectRegistry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private boolean isFromFalling = false;

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z"), argsOnly = true)
    private DamageSource modifyDamageSource(DamageSource source) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (source.isFromFalling() && player.hasStatusEffect(EffectRegistry.WOMAN_EFFECT)) {
            isFromFalling = true;
            return new DamageSource("girlpower").setBypassesArmor().setFromFalling();
        } else {
            isFromFalling = false;
        }
        return source;
    }

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z"), argsOnly = true)
    private float modifyDamage(float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.hasStatusEffect(EffectRegistry.WOMAN_EFFECT) && isFromFalling) {
            return damage / 1.5f;
        }
        return damage;
    }
}
