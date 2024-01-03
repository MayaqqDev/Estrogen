package dev.mayaqq.estrogen.fabric.mixin;

import dev.mayaqq.estrogen.registry.common.EstrogenDamageSources;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class PlayerEntityMixin {
    @Unique
    private boolean isFromFalling = false;

    @ModifyArg(method = "hurt", at = @At(value = "HEAD", target = "Lnet/minecraft/world/entity/player/Player;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private DamageSource modifyDamageSource(DamageSource source) {
        Player player = (Player) (Object) this;
        if (source.is(DamageTypes.FALL) && player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
            isFromFalling = true;
            return EstrogenDamageSources.of(player.level(), EstrogenDamageSources.GIRLPOWER_DAMAGE_TYPE);
        } else {
            isFromFalling = false;
        }
        return source;
    }

    @ModifyVariable(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInvulnerableTo(Lnet/minecraft/world/damagesource/DamageSource;)Z"), argsOnly = true)
    private float modifyDamage(float damage) {
        Player player = (Player) (Object) this;
        if (player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT) && isFromFalling) {
            return damage / 1.5f;
        }
        return damage;
    }
}
