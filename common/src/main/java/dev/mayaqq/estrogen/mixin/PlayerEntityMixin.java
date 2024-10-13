package dev.mayaqq.estrogen.mixin;

import dev.mayaqq.estrogen.config.ChestConfig;
import dev.mayaqq.estrogen.config.PlayerEntityExtension;
import dev.mayaqq.estrogen.registry.EstrogenAttributes;
import dev.mayaqq.estrogen.registry.EstrogenDamageSources;
import dev.mayaqq.estrogen.registry.EstrogenEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityExtension {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

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
                .add(EstrogenAttributes.SHOW_BOOBS.get())
                .add(EstrogenAttributes.BOOB_INITIAL_SIZE.get())
                .add(EstrogenAttributes.BOOB_GROWING_START_TIME.get())
                .add(EstrogenAttributes.FALL_DAMAGE_RESISTANCE.get());
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
        Player player = (Player) (Object) this;
        double attributeValue = player.getAttributeValue(EstrogenAttributes.FALL_DAMAGE_RESISTANCE.get());
        if (source.is(DamageTypeTags.IS_FALL)) {
            if (attributeValue > damage) {
                return 0.0f;
            } else {
                return (float) (damage / attributeValue);
            }
        }
        return damage;
    }
}