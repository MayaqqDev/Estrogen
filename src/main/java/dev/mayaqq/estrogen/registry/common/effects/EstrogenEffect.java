package dev.mayaqq.estrogen.registry.common.effects;

import dev.mayaqq.estrogen.networking.EstrogenStatusEffectSender;
import dev.mayaqq.estrogen.utils.Time;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

import static dev.mayaqq.estrogen.client.Dash.*;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_GROWING_START_TIME;
import static dev.mayaqq.estrogen.registry.common.EstrogenAttributes.BOOB_INITIAL_SIZE;
import static dev.mayaqq.estrogen.registry.common.EstrogenEffects.ESTROGEN_EFFECT;

public class EstrogenEffect extends StatusEffect {
    public EstrogenEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.world.isClient) {
            maxDashes = (short) (amplifier + 1);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.world.isClient) {
            maxDashes = 0;
            currentDashes = 0;
            onCooldown = false;
        }
        if (entity instanceof ServerPlayerEntity player) {
            EstrogenStatusEffectSender.sendRemovePlayerStatusEffect(player, ESTROGEN_EFFECT, PlayerLookup.tracking(player).toArray(ServerPlayerEntity[]::new));
        }

        if (!entity.hasStatusEffect(ESTROGEN_EFFECT)) {
            entity.getAttributeInstance(BOOB_INITIAL_SIZE).setBaseValue(0.0);
            entity.getAttributeInstance(BOOB_GROWING_START_TIME).setBaseValue(-1.0);
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.world.isClient) {
            maxDashes = (short) (amplifier + 1);
        }

        if (entity instanceof ServerPlayerEntity player) {
            EstrogenStatusEffectSender.sendPlayerStatusEffect(player, ESTROGEN_EFFECT, PlayerLookup.tracking(player).toArray(ServerPlayerEntity[]::new));
        }

        super.onApplied(entity, attributes, amplifier);

        EntityAttributeInstance attributeInstance = entity.getAttributeInstance(BOOB_GROWING_START_TIME);
        if (attributeInstance.getBaseValue() < 0.0) {
            double currentTime = Time.currentTime(entity.world);
            entity.getAttributeInstance(BOOB_GROWING_START_TIME).setBaseValue(currentTime);
        }
    }
}