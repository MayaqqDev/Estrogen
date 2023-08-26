package dev.mayaqq.estrogen.registry.common.effects;

import dev.mayaqq.estrogen.networking.EstrogenS2C;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

import static dev.mayaqq.estrogen.client.Dash.*;
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
            EstrogenS2C.sendRemovePlayerStatusEffect(player, ESTROGEN_EFFECT, PlayerLookup.tracking(player).toArray(ServerPlayerEntity[]::new));
        }

    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.world.isClient) {
            maxDashes = (short) (amplifier + 1);
        }
        if (entity instanceof ServerPlayerEntity player) {
            EstrogenS2C.sendPlayerStatusEffect(player, ESTROGEN_EFFECT, PlayerLookup.tracking(player).toArray(ServerPlayerEntity[]::new));
        }
        super.onApplied(entity, attributes, amplifier);
    }
}