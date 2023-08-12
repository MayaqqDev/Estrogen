package dev.mayaqq.estrogen.registry.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static dev.mayaqq.estrogen.client.Dash.*;

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
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.world.isClient) {
            maxDashes = (short) (amplifier + 1);
        }
        super.onApplied(entity, attributes, amplifier);
    }
}