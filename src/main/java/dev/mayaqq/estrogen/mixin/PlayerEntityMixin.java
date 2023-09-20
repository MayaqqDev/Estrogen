package dev.mayaqq.estrogen.mixin;

import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import io.github.fabricators_of_create.porting_lib.mixin.common.accessor.DamageSourceAccessor;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private boolean isFromFalling = false;

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z"), argsOnly = true)
    private DamageSource modifyDamageSource(DamageSource source) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (source.isFromFalling() && player.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
            isFromFalling = true;
            return girlPowerDamageSource();
        } else {
            isFromFalling = false;
        }
        return source;
    }

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z"), argsOnly = true)
    private float modifyDamage(float damage) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.hasStatusEffect(EstrogenEffects.ESTROGEN_EFFECT) && isFromFalling) {
            return damage / 1.5f;
        }
        return damage;
    }

    @Unique
    private DamageSource girlPowerDamageSource() {
        EntityDamageSource source = new EntityDamageSource("girlpower", (PlayerEntity) (Object) this);
        ((DamageSourceAccessor) source).port_lib$setDamageBypassesArmor().setFromFalling();
        return source;
    }
}
