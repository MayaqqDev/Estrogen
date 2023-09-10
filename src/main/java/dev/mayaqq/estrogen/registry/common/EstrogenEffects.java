package dev.mayaqq.estrogen.registry.common;

import dev.mayaqq.estrogen.registry.common.effects.EstrogenEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenEffects {
    public static final StatusEffect ESTROGEN_EFFECT = new EstrogenEffect(StatusEffectType.BENEFICIAL, 104164161);

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, id("estrogen"), ESTROGEN_EFFECT);
    }
}