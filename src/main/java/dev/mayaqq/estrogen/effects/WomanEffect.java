package dev.mayaqq.estrogen.effects;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import static dev.mayaqq.estrogen.client.ClientEventRegistry.currentDashes;
import static dev.mayaqq.estrogen.client.ClientEventRegistry.maxDashes;

public class WomanEffect extends StatusEffect {
    public WomanEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof ClientPlayerEntity) {
            maxDashes = (short) (amplifier + 1);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof ClientPlayerEntity) {
            maxDashes = 0;
            currentDashes = 0;
        }
    }
}