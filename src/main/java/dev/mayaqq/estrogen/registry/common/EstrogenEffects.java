package dev.mayaqq.estrogen.registry.common;

import dev.mayaqq.estrogen.registry.common.effects.EstrogenEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.registry.Registry;

import static dev.mayaqq.estrogen.Estrogen.id;

public class EstrogenEffects {
    public static final StatusEffect ESTROGEN_EFFECT = new EstrogenEffect(StatusEffectCategory.BENEFICIAL, 104164161);

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, id("estrogen"), ESTROGEN_EFFECT);
    }
}