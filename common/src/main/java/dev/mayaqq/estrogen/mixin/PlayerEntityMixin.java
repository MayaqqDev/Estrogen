package dev.mayaqq.estrogen.mixin;

import dev.architectury.networking.NetworkManager;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenC2S;
import dev.mayaqq.estrogen.registry.common.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.common.EstrogenDamageSources;
import dev.mayaqq.estrogen.registry.common.EstrogenEffects;
import dev.mayaqq.estrogen.utils.Estromath;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {
    /*
     * Registers additional attributes for players without the use of events.
     * Should be compatible with any other mod.
     */
    @Inject(method = "createAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;", require = 1, allow = 1, at = @At("RETURN"))
    private static void additionalEntityAttributes$addPlayerAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
                .add(EstrogenAttributes.DASH_LEVEL.get())
                .add(EstrogenAttributes.BOOB_INITIAL_SIZE.get())
                .add(EstrogenAttributes.BOOB_GROWING_START_TIME.get());
    }

    // Modifies the damage source of fall damage if the player has the estrogen effect.
     @ModifyVariable(
            method = "hurt",
            at = @At(value = "HEAD"),
            index = 1,
            argsOnly = true
    )
    private DamageSource modifyDamageSource(DamageSource source) {
        Player player = (Player) (Object) this;
        if (source.is(DamageTypes.FALL) && player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT)) {
            return EstrogenDamageSources.of(player.level(), EstrogenDamageSources.GIRLPOWER_DAMAGE_TYPE);
        }
        return source;
    }

    // If the damage source is the aforementioned damage source, reduce the damage by 1/3.
    @ModifyVariable(
            method = "hurt",
            at = @At(value = "HEAD"),
            index = 2,
            argsOnly = true
    )
    private float modifyFallDamage(float damage, DamageSource source) {
        if (source.is(EstrogenDamageSources.GIRLPOWER_DAMAGE_TYPE)) {
            return damage / 1.5f;
        }
        return damage;
    }

    @Inject(method = "interactOn", at = @At("TAIL"), cancellable = true)
    private void estrogen$interactOn(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = (Player) (Object) this;
        if (player.level().isClientSide && EstrogenConfig.client().entityPatting.get() && player.isCrouching() && player.getItemInHand(interactionHand).isEmpty()) {
            FriendlyByteBuf buf = new FriendlyByteBuf(PacketByteBufs.create());
            buf.writeLongArray(new long[]{Estromath.doubleToLong(entity.getX()), Estromath.doubleToLong(entity.getY() + entity.getEyeHeight() + 0.5), Estromath.doubleToLong(entity.getZ())});
            if (entity instanceof Mob mob) {
                buf.writeResourceLocation(mob.getAmbientSound().getLocation());
            } else {
                buf.writeResourceLocation(Estrogen.id("empty"));
            }
            NetworkManager.sendToServer(EstrogenC2S.SPAWN_HEARTS, buf);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}