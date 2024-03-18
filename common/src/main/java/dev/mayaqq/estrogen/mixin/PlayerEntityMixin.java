package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.Estrogen;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.config.EstrogenConfig;
import dev.mayaqq.estrogen.networking.EstrogenNetworkManager;
import dev.mayaqq.estrogen.networking.messages.c2s.SpawnHeartsPacket;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.EstrogenDamageSources;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEntityMixin implements PlayerEntityExtension {
    @Unique
    public ChestConfig estrogenChestConfig = null;

    @Unique
    public ChestConfig estrogen$getChestConfig() {
        return this.estrogenChestConfig;
    }

    @Unique
    public void estrogen$setChestConfig(ChestConfig chestConfig) {
        this.estrogenChestConfig = chestConfig;
    }

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
        if (source.is(DamageTypes.FALL) && player.hasEffect(EstrogenEffects.ESTROGEN_EFFECT.get())) {
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
            ResourceLocation sound = Estrogen.id("empty");
            if (entity instanceof Mob mob) {
                if (mob.getAmbientSound() != null) {
                    sound = mob.getAmbientSound().getLocation();
                }
            }
            EstrogenNetworkManager.CHANNEL.sendToServer(new SpawnHeartsPacket(entity.position(), sound));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}